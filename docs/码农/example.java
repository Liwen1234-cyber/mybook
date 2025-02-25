import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String beginStr = scanner.next();
        String endStr = scanner.next();
        scanner.nextLine();
        
        List<String> Str = new ArrayList<>();
        Str.add(beginStr);
        Str.add(endStr);
        for(int i = 0; i < n; i++){
            Str.add(scanner.nextLine());
        }
        int cnt = dfs(Str, beginStr, endStr);

        System.out.println(cnt);

        scanner.close();

    }

    private static int dfs(List<String> Str, String beginStr, String endStr){
        int len = 1;
        Set<String> visited = new HashSet<>();
        Set<String> set = new HashSet<>(Str);
        Queue<String> queue = new LinkedList<>();
        visited.add(beginStr);
        queue.add(beginStr);
        queue.add(null);
        while(!queue.isEmpty()){
            String curStr = queue.remove();
            
            if(curStr == null){
                if(!queue.isEmpty()){
                    len++;
                    queue.add(null);
                }
                continue;
            }

            char[] ss = curStr.toCharArray();
            for(int i = 0; i < ss.length; i++){
            
                char old = ss[i];
                for(char j = 'a'; j <= 'z'; j++){
                    ss[i] = j;
                    String newStr = new String(ss);
                    if(set.contains(newStr) && !visited.contains(newStr)){
                        queue.add(newStr);
                        visited.add(newStr);
                        if(newStr.equals(endStr)){
                            return len+1;
                        }
                    }
                }
                ss[i] = old;
            }
        }
        return 0;
    }
    
}