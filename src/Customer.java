import java.util.HashSet;


public class Customer {
	long id;
	//int[] categories = new int[1000];
	//int catLength = 0;
	HashSet<Integer> categories = new HashSet<Integer>();
	double amount;
	int numberPurchases;
	public Customer(long id,int[] categories){
		this.id = id;
		
		//for(int i = 0; i < categories.length;i++)
			//this.categories[categories[i]] = categories[i];
		int item;
		int i = 0;
		while((item = categories[i++]) != 0){
			this.categories.add(item);
		}	
		
		//this.catLength = categories.length;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public HashSet<Integer> getCategories() {
		return categories;
	}
	public void setCategories(HashSet<Integer> categories) {
		this.categories = categories;
	}
	
	/*public int[] getCategories() {
		return categories;
	}
	public void setCategories(int[] categories) {
		this.categories = categories;
	}*/
	public double getAmount() {
		return amount;
	}
	public void addAmount(double amount) {
		numberPurchases++;
		this.amount += amount;
	}
	public int getNumberPurchases() {
		return numberPurchases;
	}
	public void setNumberPurchases(int count) {
		this.numberPurchases = count;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((categories == null) ? 0 : categories.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (categories == null) {
			if (other.categories != null)
				return false;
		} else if (!categories.equals(other.categories))
			return false;
		return true;
	}
	
	
}
