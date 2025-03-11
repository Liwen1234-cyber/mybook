class Solution {
    public int max, len;
    public Set<String> set = new HashSet<>();
    public String ss;

    public List<String> removeInvalidParentheses(String s) {
        ss = s;

        int l = 0, r = 0; // l: 多余左括号的数量，r: 多余右括号的数量
        for(char i : s.toCharArray()){
            if(i == '(') l++;
            else if(i == ')'){
                if(l > 0) l--;
                else r++;
            }
        }
        len = s.length() - l - r;

        int cur_l = 0, cur_r = 0;
        for(char i : s.toCharArray()){
            if(i == '(') cur_l++;
            else if(i == ')') cur_r++;
        }
        max = Math.min(cur_l - l, cur_r - r);

        dfs("", 0, 0, l, r);
        return new ArrayList<>(set);

    }

    public void dfs(String cur, int index, int score, int l, int r){ 
        if(score < 0 || score > max || l < 0 || r < 0) return;

        if(index == ss.length()){
            if(score == 0 && cur.length() == len) set.add(cur);
            return;
        }

        if(ss.charAt(index) == '('){
            dfs(cur + "(", index + 1, score + 1, l, r);
            dfs(cur, index + 1, score, l - 1, r);
        }
        else if(ss.charAt(index) == ')'){
            dfs(cur + ")", index + 1, score - 1, l, r);
            dfs(cur, index + 1, score, l, r - 1);
        }
        else{
            dfs(cur + ss.charAt(index), index + 1, score, l, r);
        }

    }
}