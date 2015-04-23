import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class test {

	public static void main(String[] args) {
		HashSet<Integer> probA = new HashSet<Integer>();
		HashSet<Integer> probB = new HashSet<Integer>();
		
		//probA.add(1);
		probA.add(3);
		
		probB.add(1);
		probB.add(2);
		
		System.out.println(probB.remove(1));
		System.out.println(probB.remove(3));
		/*System.out.println(probA.equals(probB));
		System.out.println(probA.hashCode());
		System.out.println(probB.hashCode());
		System.out.println("--------------");
		
		int[] a = {0,0,1,2};
		int[] b = {0,0,1,2};
		
		System.out.println(a.equals(b));
		int[] temp = {1,2,3};
		List<Integer> t = Arrays.asList(tem);
		Set<Integer> mySet = new HashSet<Integer>(Arrays.asList(temp));
		
		HashSet<Integer> ll = new HashSet<Integer>();
		Collections.addAll(ll,temp);
		
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Long.MAX_VALUE);
		
		
		Customer custA = new Customer(11,new int[]{1,2,3,4});
		Customer custB = new Customer(22,new int[]{1,2,3,4});
		Customer custC = new Customer(33,new int[]{6,4});
		System.out.println("---------------------");
		System.out.println(custA.equals(custB));
		System.out.println(custA.hashCode());
		System.out.println(custB.hashCode());
		System.out.println(custC.hashCode());*/
		
	}

}
