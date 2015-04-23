
import java.util.*;
import java.io.*;

/** Sample driver code for Project 3.  Modify as needed.
 *  Do not change input/output formats.
 *  @author rbk
 */
public class MultidimensionalSearch {
	HashMap<Long,Customer> customerData = new HashMap<Long,Customer>();

	static int[] categories;
	static final int NUM_CATEGORIES = 1000, MOD_NUMBER = 997;
	static int DEBUG = 9;
	private int phase = 0;
	private long startTime, endTime, elapsedTime;

	public static void main(String[] args)  throws FileNotFoundException {
		categories = new int[NUM_CATEGORIES];
		Scanner in;
		if(args.length > 0) {
			in = new Scanner(new File(args[0]));
		} else {
			in = new Scanner(System.in);
		}
		MultidimensionalSearch x = new MultidimensionalSearch();
		x.timer();
		long rv = x.driver(in);
		System.out.println(rv);
		x.timer();

	}

	/** Read categories from in until a 0 appears.
	 *  Values are copied into static array categories.  Zero marks end.
	 * @param in : Scanner from which inputs are read
	 * @return : Number of categories scanned
	 */
	public static int readCategories(Scanner in) {
		int cat = in.nextInt();
		int index = 0;
		while(cat != 0) {
			categories[index++] = cat;
			cat = in.nextInt();
		}
		categories[index] = 0;
		return index;
	}

	public long driver(Scanner in) {
		String s;
		long rv = 0, id;
		int cat;
		double purchase;

		while(in.hasNext()) {
			s = in.next();
			if(s.charAt(0) == '#') {
				s = in.nextLine();
				continue;
			}
			if(s.equals("Insert")) {
				id = in.nextLong();
				readCategories(in);
				rv += insert(id, categories);
			} else if(s.equals("Find")) {
				id = in.nextLong();
				rv += find(id);
			} else if(s.equals("Delete")) {
				id = in.nextLong();
				rv += delete(id);
			} else if(s.equals("TopThree")) {
				cat = in.nextInt();
				rv += topthree(cat);
			} else if(s.equals("AddInterests")) {
				id = in.nextLong();
				readCategories(in);
				rv += addinterests(id, categories);
			} else if(s.equals("RemoveInterests")) {
				id = in.nextLong();
				readCategories(in);
				rv += removeinterests(id, categories);
			} else if(s.equals("AddRevenue")) {
				id = in.nextLong();
				purchase = in.nextDouble();
				rv += addrevenue(id, purchase);
			} else if(s.equals("Range")) {
				double low = in.nextDouble();
				double high = in.nextDouble();
				rv += range(low, high);
			} else if(s.equals("SameSame")) {
				rv += samesame();
			} else if(s.equals("NumberPurchases")) {
				id = in.nextLong();
				rv += numberpurchases(id);
			} else if(s.equals("End")) {
				return rv % 997;
			} else {
				System.out.println("Houston, we have a problem.\nUnexpected line in input: "+ s);
				System.exit(0);
			}
		}
		// This can be inside the loop, if overflow is a problem
		rv = rv % MOD_NUMBER;
		TestUtility.compareCustomerCategories(customerData);
		return rv;
	}

	public void timer()
	{
		if(phase == 0) {
			startTime = System.currentTimeMillis();
			phase = 1;
		} else {
			endTime = System.currentTimeMillis();
			elapsedTime = endTime-startTime;
			System.out.println("Time: " + elapsedTime + " msec.");
			memory();
			phase = 0;
		}
	}

	public void memory() {
		long memAvailable = Runtime.getRuntime().totalMemory();
		long memUsed = memAvailable - Runtime.getRuntime().freeMemory();
		System.out.println("Memory: " + memUsed/1000000 + " MB / " + memAvailable/1000000 + " MB.");
	}


	int insert(long id, int[] categories) { 
		if(customerData.containsKey(id))
			return -1;

		customerData.put(id, new Customer(id,categories));
		return 1;
	}

	int find(long id) {
		Customer cust = customerData.get(id);
		if(cust == null)
			return  -1;
		else
			return (int) cust.getAmount(); 
	}
	int delete(long id) {
		Customer cust = customerData.get(id);
		if(cust == null)
			return -1;
		else
		{
			int amount = (int) cust.getAmount();
			customerData.remove(id);
			return amount;
		}
	}
	/*
	 * Takes O(n) time to find out the top 3.
	 * No tree sets needed since the comparisons can be done while traversing and keeping track of the top 3 amounts.
	 */
	int topthree(int cat) {
		/* 
		 * Try with tree set and without tree set.
		 * Since we are checking for top three, we can have 3 check conditions and continue.
		 * This might guarantee O(n) running time. 
		 */

		// With TreeSet
		/*TreeSet<Long> resultSet = new TreeSet<Long>(new Comparator<Long>() {

			@Override
			public int compare(Long o1, Long o2) {
				return (int) (customerData.get(o1).getAmount()*100 - customerData.get(o2).getAmount()*100);
			}
		});*/

		double top1 = 0;
		double top2 = -1;
		double top3 = -2;

		for(Map.Entry<Long, Customer> item : customerData.entrySet()){
			// Logic using int[] for categories
			//if(item.getValue().getCategories()[cat]!=0)
			// Logic using HashSet for categories
			if(item.getValue().getCategories().contains(cat))
			{
				//resultSet.add(item.getKey());

				// without treeSet
				double resultAmount = item.getValue().getAmount();
				if(Double.compare(resultAmount,top1) > 0){
					top3 = top2;
					top2 = top1;
					top1 = resultAmount;
				}else if(Double.compare(resultAmount,top2) > 0){
					top3 = top2;
					top2 = resultAmount;
				}else if(Double.compare(resultAmount,top3) > 0){
					top3 = resultAmount;
				}
			}

		}

		// The comparison for double is doubtful, but it should work.
		if(top2 < 0){
			top2 = 0;
			top3 = 0;
		}
		if(top3 < 0){
			top3 = 0;
		}

		int totalAmount = (int) (top1 + top2 + top3);

		// calculate totatAmount for TreeSet Implementation
		/*
		Double max1 = customerData.get(resultSet.pollLast()).getAmount();
		Double max2 = customerData.get(resultSet.pollLast()).getAmount();
		Double max3 = customerData.get(resultSet.pollLast()).getAmount(); // will throw NPE if result < 3
		
		double sum = 0;
		if(max1!=null)
			sum+=max1;
		if(max2!=null)
			sum+=max2;
		if(max3!=null)
			sum+=max3;*/
		/*if((int)sum != totalAmount){
			System.out.println("haha");
		}*/
		
		/*System.out.println(totalAmount);
		if(totalAmount == 1013)
			System.out.println("HAHA");*/
		
		return totalAmount;
		//return (int)sum;
	}

	int addinterests(long id, int[] categories) {

		Customer cust = customerData.get(id);
		if(cust == null)
			return  -1;

		// Logic using int[] for categories
		//int[] custCategories = cust.getCategories();

		HashSet<Integer> custCategories = cust.getCategories();
		int newCount = 0;
		int item;
		int i = 0;
		while((item = categories[i++]) != 0){
			//int item = categories[i];
			/*if(custCategories[item]==0)
			{
				custCategories[item] = item;
				newCount++;
			}*/

			if(custCategories.add(item)){
				newCount++;
			}

		}
		return newCount; 
	}
	int removeinterests(long id, int[] categories) {
		Customer cust = customerData.get(id);
		if(cust == null)
			return  -1;

		// Logic using int[] for categories
		//int[] custCategories = cust.getCategories();

		HashSet<Integer> custCategories = cust.getCategories();
		
		//for(int i = 0; i < categories.length; i++){
			//int item = categories[i];
		int item;
		int i = 0;
		while((item = categories[i++]) != 0){	
		/*if(custCategories[item]!=0)
			{
				custCategories[item] = 0;
				newCount++;
			}*/

			custCategories.remove(item);
		}
		//cust.setCatLength(cust.getCatLength() - newCount);

		return custCategories.size(); 
	}
	int addrevenue(long id, double purchase) {
		Customer cust = customerData.get(id);
		if(cust == null)
			return  -1;

		cust.addAmount(purchase);
		return (int) cust.getAmount();
	}

	// Following functions are optional for CS 4V95.019:
	int range(double low, double high) {
		int count = 0;
		
		/*
		 * Iterate over the customerData HashMap : takes O(N)
		 * And compare the amount.
		 */
		for(Map.Entry<Long, Customer> item : customerData.entrySet()){
			double amount = item.getValue().getAmount();
			if(Double.compare(amount,low) >= 0 && Double.compare(amount,high) <= 0)
				count++;
		}

		return count;
	}
	int samesame() {
		ArrayList<Long> customerWith5PlusCategories = new ArrayList<Long>();

		/*
		 * Find all customers with 5 or more categories of interest
		 */
		for(Map.Entry<Long,Customer> item : customerData.entrySet()){
			if(item.getValue().getCategories().size()>=5)
				customerWith5PlusCategories.add(item.getKey());
		}
		
		/*
		 * Iterate over the filtered result and Check for matching customers with respect to their categories.
		 * At each iteration, compare the first item with others. If similar items are found,
		 * At them to a temporary arrayList. This needs to be done, since we cannot remove the items directly which
		 * will cause concurrent modification exception.
		 */
		int count = 0;
		ArrayList<Long> temp = new ArrayList<Long>();

		while(!customerWith5PlusCategories.isEmpty())
		{
			Long current = customerWith5PlusCategories.get(0);
			for(int i = 1; i < customerWith5PlusCategories.size(); i++){
				Long itemId = customerWith5PlusCategories.get(i);
				/*
				 * We directly compare the customer object since we have implemented
				 * an equals method of the Customer for match categories.
				 */
				if(customerData.get(current).equals(customerData.get(itemId)));
					temp.add(itemId);
			}
			/*
			 * If we find any matching items we remove them from the resultSet.
			 */
			if(!temp.isEmpty()){
				count = temp.size() + 1 ; // Get the number of identical customers found + 1 for the current item
				customerWith5PlusCategories.removeAll(temp);
				temp.clear();
			}
			customerWith5PlusCategories.remove(current);			
		}

		return count;
	}
	int numberpurchases(long id) {
		Customer cust = customerData.get(id);
		if(cust == null)
			return  -1;
		else
			return cust.getNumberPurchases();
	}
}