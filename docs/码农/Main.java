import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Main {
    static class SHA256{
        public static String SHA256(String input) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

                StringBuilder hexString = new StringBuilder();
                for (byte b : hashBytes) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) {
                        hexString.append('0');
                    }
                    hexString.append(hex);
                }

                return hexString.toString();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class Merkletree{
        Merkletree left, right;
        String val;
        String path;
        public Merkletree(String val){
            this.val = val;
        }
        public Merkletree(String val, String path){
            this.val = val;
            this.path = path;
        }

        public Merkletree(String val, Merkletree left, Merkletree right){
            this.val = val;
            this.left = left;
            this.right = right;
        }

    }
    public static int bitcont(int num){
        int count = 0;
        while(num > 0){
            count++;
            num &= (num-1);
        }
        return count;
    }
    public static String dectobinary(int path_num, int bitnum){
        int num = path_num;
        StringBuffer sb = new StringBuffer();
        while(num > 0){
            sb.insert(0, num & 1);
            num >>= 1;
        }
        while(sb.length() < bitnum){
            sb.insert(0, '0');
        }
        String s = sb.toString();
        return s;
    }

    public static Merkletree buildMerkletree(List<String> list){
        Queue<Merkletree> queue = new LinkedList<>();
        SHA256 sha256 = new SHA256();
        int path_num = 0;
        int bitnum = (int)Math.ceil(Math.log(list.size())/Math.log(2));
        for(String s : list){
            queue.add(new Merkletree(sha256.SHA256(s), dectobinary(path_num, bitnum)));
            path_num++;
        }

        while(queue.size() > 1){
            int len = queue.size();
            for(int i = 0; i < len/2; i++){
                Merkletree left = queue.poll();
                Merkletree right = queue.poll();
                queue.add(new Merkletree(sha256.SHA256(left.val + right.val), left, right));
            }
            if((len & 1) == 1){
                queue.add(queue.poll());
            }
        }
        return queue.poll();
    }

    public static String verify (String new_hash, String new_path, String hashes){
        String[] arr = hashes.split("-");
        String cur = new_hash;
        int path_num = Integer.parseInt(new_path, 2);
        for(int i = arr.length-1; i >= 0; i--){
            if((path_num & 1) == 1) cur = SHA256.SHA256(arr[i]+cur);
            else cur = SHA256.SHA256(cur+arr[i]);
            path_num >>= 1;
        }
        return cur;
    }

    public static String backtracking(Merkletree node, String hash){
        if(node == null) return "";
        if(node.val.equals(hash)) return "true_" + node.path;

        String res = "";
        String left1 = backtracking(node.left, hash);
        if(left1.length() > 0){
            if(node.right != null) res += node.right.val + "-";

            if(!left1.substring(0, 4).equals("true")) res += left1;
            else res += left1.substring(4);

        }
        String right1 = backtracking(node.right, hash);
        if(right1.length() > 0) {
            if (node.left != null) res += node.left.val + "-";

            if (!right1.substring(0, 4).equals("true")) res += right1;
            else res += right1.substring(4);
        }

        return res;
    }

    public static String backtracking(Merkletree node, String hash, String new_data){
        if(node == null) return "";
        if(node.val.equals(hash)){
            if(new_data.length() != 0) node.val = SHA256.SHA256(new_data);
            return "true_" + node.path;
        }
        String temp = "";

        String res = "";
        String left1 = backtracking(node.left, hash, new_data);
        if(left1.length() > 0){
            if(node.right != null){
                res += node.right.val + "-";
                temp = node.right.val;
            }

            if(!left1.substring(0, 4).equals("true")){
                res += left1;
                node.left.val = new_data;
                new_data = SHA256.SHA256(node.left.val+temp);
            }
            else{
                res += left1.substring(4);
                node.left.val = SHA256.SHA256(new_data);
                new_data = SHA256.SHA256(node.left.val+temp);
            }

        }
        String right1 = backtracking(node.right, hash, new_data);
        if(right1.length() > 0) {
            if (node.left != null){
                res += node.left.val + "-";
                temp = node.left.val;
            }

            if (!right1.substring(0, 4).equals("true")){
                res += right1;
                node.right.val = new_data;
                new_data = SHA256.SHA256(temp+node.right.val);
            }
            else{
                res += right1.substring(4);
                node.right.val = SHA256.SHA256(new_data);
                new_data = SHA256.SHA256(temp+node.right.val);
            }
        }

        return res;
    }



    public static Merkletree bfs(Merkletree node){
        if(node == null) return null;
        Deque<Merkletree> deque = new LinkedList<>();
        deque.add(node);
        int num = 1;
        while(!deque.isEmpty()){
            int len = deque.size();
            if(len != num){
                if((len & 1) == 1) return deque.pollLast();
                else{
                    deque.pollLast();
                    return deque.pollLast();
                }
            }
            for(int i = 0; i < len; i++){
                Merkletree cur = deque.poll();
                if(cur.left != null) deque.add(cur.left);
                if(cur.right != null) deque.add(cur.right);
            }
        }
        return node;
    }

    public static void method(String action, List<String> list){
        String[] a = action.split("\\s+");
        SHA256 sha256 = new SHA256();
        Merkletree root = buildMerkletree(list);
        if(a[0].equals("construct")){ // construct
            System.out.println(root.val);
        }
        else if(a[0].equals("proof")){
            String[] res = backtracking(root, sha256.SHA256(a[1])).split("_");
            if(res.length < 2){
                System.out.println("nil");
                return;
            }
            System.out.print(res[1]);
            System.out.print(" ");
            System.out.println(res[0].substring(0, res[0].length()-1));
        }
        else if(a[0].equals("fetch")){
            String temp_path = a[1];
            int num = Integer.parseInt(temp_path,2);
            System.out.print(list.get(num));
            System.out.print(" ");
            String[] res = backtracking(root, sha256.SHA256(list.get(num))).split("_");
            if(res.length < 2){
                System.out.println("nil");
                return;
            }
            System.out.println(res[0].substring(0, res[0].length()-1));

        }
        else if(a[0].equals("verify")){
            String root1_val = a[1];
            String new_data = a[2];
            String new_path = a[3];
            String new_hash = a[4];
            System.out.println(verify(sha256.SHA256(new_data), new_path, new_hash).equals(root1_val));
        }
        else if(a[0].equals("update")){
            String new_path = a[1];
            String new_data = a[2];
            int num = Integer.parseInt(new_path,2);
            String[] res = backtracking(root, sha256.SHA256(list.get(num)), new_data).split("_");
            root.val = verify(sha256.SHA256(new_data), new_path, res[0].substring(0, res[0].length()-1));
            System.out.println(root.val);

        }
        else if(a[0].equals("add")){
            String new_data = a[1];
            list.add(new_data);
            Merkletree new_node = buildMerkletree(list);
            System.out.println(new_node.val);
        }
        else if(a[0].equals("delete")) {
            String new_path = a[1];
            int num = Integer.parseInt(new_path,2);
            String[] res = backtracking(root, sha256.SHA256(list.get(num))).split("_");

        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        List<String> list = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        in.nextLine();
        for (int i = 0; i < n; i++) {
            list.add(in.nextLine());
        }
        for (int i = 0; i < m; i++) {
            actions.add(in.nextLine());
            if(actions.get(i).startsWith("verify")){
                if(actions.get(i).split("\\s+").length < 5){
                    actions.set(i, actions.get(i) + " " + in.nextLine());
                }
            }
            else if(actions.get(i).startsWith("update")){
                if(actions.get(i).split("\\s+").length < 3){
                    actions.set(i, actions.get(i) + " " + in.nextLine());
                }
            }
        }
        in.close();

        for(String action : actions){
            method(action, list);
        }

    }
}
