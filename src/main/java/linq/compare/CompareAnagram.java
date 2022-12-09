package linq.compare;

import java.util.HashMap;

public class CompareAnagram  {
    public static boolean isAnagram(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }
        HashMap<Character,Integer> map = new HashMap<Character,Integer>();
        for (int i=0; i<s.length(); i++) {
            char ch = s.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0)+1);
        }
        for (int j=0; j<t.length(); j++) {
            char ch = t.charAt(j);
            if (!map.containsKey(ch)) {
                return false;
            }
            map.put(ch, map.get(ch)-1);
            if (map.get(ch) == 0) {
                map.remove(ch);
            }
        }
        return map.isEmpty();
    }
}
