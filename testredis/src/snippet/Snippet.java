package snippet;

import java.util.Stack;

import org.junit.Test;

public class Snippet {
	  public static void main(String[] args) {
	        // TODO Auto-generated method stub
	       // String  s="(1){CHI[��|��] || CAT[J] LOGIC[G|D]}+(2){CAT[A] || OF_AMBI[A]}+(3){CHI[��]||CAT[N]}";
	        String s="(}";
		  Stack<Character> sc=new Stack<Character>();
	        char[] c=s.toCharArray();
	        for (int i = 0; i < c.length; i++) {
	            if (c[i]=='('||c[i]=='['||c[i]=='{') {
	                sc.push(c[i]);
	            }
	            else if (c[i]==')') {
	                if (sc.peek()=='(') {
	                    sc.pop();
	                }
	            }else if (c[i]==']') {
	                if (sc.peek()=='[') {
	                    sc.pop();
	                }
	            }else if (c[i]=='}') {
	                if (sc.peek()=='{') {
	                    sc.pop();
	                }
	            }
	        }
	        if (sc.empty()) {
	            System.out.println("�ɶ�");
	        }else {
	            System.out.println("���ɶ�");
	        }
	    }
	  @Test
	  public void testStr(){
		  String strs="wiudjfds(){}";
		  String str="{}";
		  boolean isok=strs.contains(str);
		  System.out.print(isok);
	  }
}

