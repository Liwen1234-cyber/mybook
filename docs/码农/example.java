import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int points = scanner.nextInt();
        int edges = scanner.nextInt();
        Disjoint disjoint = new Disjoint(points+1);
        for(int i = 0; i < edges; i++){
            disjoint.join(scanner.nextInt(), scanner.nextInt());
        
        }

        if(disjoint.isSame(scanner.nextInt(), scanner.nextInt())){
            System.out.println(1);
        }
        else{
            System.out.println(0);
        }

        scanner.close();
    }
}

class Disjoint{
    private int[] father;

    public Disjoint(int n){
        father = new int[n];
        for(int i = 0; i < n; i++){
            father[i] = i;
        }
    }

    public int find(int x){
        return father[x] == x ? x : find(father[x]);
    }

    public void join(int x, int y){ // x -> y
        int fx = find(x);
        int fy = find(y);
        father[fx] = fy;
    }

    public boolean isSame(int x, int y){
        return find(x) == find(y);
    }
}
