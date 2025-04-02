import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Main_MerkleTree {
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
            this.path = left.path + right.path;
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

    public static void add(Merkletree root, List<String> list, String new_data) {
        Queue<Merkletree> queue = new LinkedList<>();
        queue.add(root);
        SHA256 sha256 = new SHA256();

        while (!queue.isEmpty()) {
            Merkletree node = queue.poll();

            // 找到第一个没有右孩子的内部节点
            if (node.left != null && node.right == null) {
                int bitnum = (int)Math.ceil(Math.log(list.size()+1)/Math.log(2));
                node.right = new Merkletree(sha256.SHA256(new_data), dectobinary(list.size(), bitnum));
                node.left = new Merkletree(node.val, node.path);
                node.path = "";

                list.add(new_data);
                break;
            } else if (node.left == null) { // 找到没有左孩子的节点
                int bitnum = (int)Math.ceil(Math.log(list.size()+1)/Math.log(2));
                int index = Integer.parseInt(node.path, 2);
                node.right = new Merkletree(sha256.SHA256(new_data), dectobinary(index+1, bitnum));
                node.left = new Merkletree(node.val, node.path);
                node.path = "";

                list.add(index+1, new_data);
                break;
            }

            queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }

        // 更新哈希值
        updateTreeHash(root);
    }

    public static void delete(Merkletree root, List<String> list, String path) {
        int index = Integer.parseInt(path, 2);
        if (index >= list.size()) {
            System.out.println("nil");
            return;
        }

        list.remove(index);

        // 重新构建受影响部分
        Queue<Merkletree> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Merkletree node = queue.poll();

            if (node.left != null && node.left.path.equals(path)) {
                node.left = (node.right != null) ? node.right : null;
                node.right = null;
                break;
            }
            if (node.right != null && node.right.path.equals(path)) {
                node.right = null;
                break;
            }

            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }

        // 更新哈希值
        updateTreeHash(root);
    }

    public static void updateTreeHash(Merkletree node) {
        if (node == null) return;
        SHA256 sha256 = new SHA256();

        if (node.left != null) updateTreeHash(node.left);
        if (node.right != null) updateTreeHash(node.right);

        if (node.left != null && node.right != null) {
            node.val = sha256.SHA256(node.left.val + node.right.val);
        } else if (node.left != null) {
            node.val = node.left.val;
            node.path = node.left.path;
            node.left = null;
        }
    }
    public static Merkletree add(Merkletree root, String newData) {
        SHA256 sha256 = new SHA256();
        Merkletree newNode = new Merkletree(sha256.SHA256(newData));
        Queue<Merkletree> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Merkletree node = queue.poll();
            if (node.left == null) {
                node.left = newNode;
                node.right = new Merkletree(node.val, node.path);
                node.path = "";
                break;
            } else if (node.right == null) {
                node.right = newNode;
                break;
            } else {
                queue.add(node.left);
                queue.add(node.right);
            }
        }
        return buildMerkletree(getLeafValues(root));
    }

    public static Merkletree delete(Merkletree root, String targetHash) {
        Queue<Merkletree> queue = new LinkedList<>();
        queue.add(root);
        Merkletree parent = null, target = null;
        boolean isLeftChild = false;
        while (!queue.isEmpty()) {
            Merkletree node = queue.poll();
            if (node.left != null) {
                if (node.left.val.equals(targetHash)) {
                    parent = node;
                    target = node.left;
                    isLeftChild = true;
                    break;
                }
                queue.add(node.left);
            }
            if (node.right != null) {
                if (node.right.val.equals(targetHash)) {
                    parent = node;
                    target = node.right;
                    isLeftChild = false;
                    break;
                }
                queue.add(node.right);
            }
        }
        if (parent != null) {
            if (isLeftChild) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }
        return buildMerkletree(getLeafValues(root));
    }


    private static List<String> getLeafValues(Merkletree root) {
        List<String> leaves = new ArrayList<>();
        Queue<Merkletree> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Merkletree node = queue.poll();
            if (node.left == null && node.right == null) {
                leaves.add(node.val);
            } else {
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
        }
        return leaves;
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
            temp = node.left.val;
            if(node.right != null){
                res += node.right.val + "-";
                temp += node.right.val;
            }

            if(!left1.substring(0, 4).equals("true")) res += left1;
            else res += left1.substring(4);

        }
        String right1 = backtracking(node.right, hash, new_data);
        if(right1.length() > 0) {
            temp = node.right.val;
            if (node.left != null){
                res += node.left.val + "-";
                temp = node.left.val + temp;
            }

            if (!right1.substring(0, 4).equals("true")) res += right1;
            else res += right1.substring(4);
        }
        if(temp.length() > 0) node.val = SHA256.SHA256(temp);

        return res;
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
            System.out.println(res[1]);
            System.out.println(res[0].substring(0, res[0].length()-1));
        }
        else if(a[0].equals("fetch")){
            String temp_path = a[1];
            int num = Integer.parseInt(temp_path,2);
            System.out.println(list.get(num));
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
            list.set(num, new_data);
            System.out.println(root.val);
        }
        else if(a[0].equals("add")){
            String new_data = a[1];
            add(root, list, new_data);
            System.out.println(root.val);
        }
        else if(a[0].equals("delete")) {
            String new_path = a[1];
            delete(root, list, new_path);
            System.out.println(root.val);

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
