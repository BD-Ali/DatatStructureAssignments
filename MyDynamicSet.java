/**
 * @param <T> The type of the satellite data of the elements in the dynamic-set.
 */
public class MyDynamicSet<T> {
	MyLinkedList<T> list;

	/*
     * You may add any fields that you wish to add.
     * Remember that the use of built-in Java classes is not allowed,
     * the only variables types you can use are: 
     * 	-	the given classes in the assignment
     * 	-	basic arrays
     * 	-	primitive variables
     */


	/**
	 * The constructor should initiate an empty dynamic-set.
	 * @param N The maximum number of elements in the dynamic set at each time.
	 */
	public MyDynamicSet(int N) {
		this.list = new MyLinkedList<>();
	}


	public Element<T> search(int k) {
		return list.search(k);
	}

	public void insert(Element<T> x) {
		ListLink<T> NewE = new ListLink<>(x);
		ListLink<T> curr = list.getHead();
		if (curr == null) {
			list.setTail(NewE);
			list.setHead(NewE);
		}
		else if (search(x.key()) == null) {
			while (curr != null && curr.key() < NewE.key()) {
				curr = curr.getNext();
			}
			if (curr == null){
				list.getTail().setNext(NewE);
				NewE.setPrev(list.getTail());
				list.setTail(NewE);
			}
			else if (curr == list.getHead()) {
				NewE.setNext(curr);
				curr.setPrev(NewE);
				list.setHead(NewE);
			}
			else {
				ListLink<T> temp = curr.getPrev();
				temp.setNext(NewE);
				NewE.setPrev(temp);
				curr.setPrev(NewE);
				NewE.setNext(curr);
			}
		}

	}
	
	public void delete(Element<T> x) {
		list.delete((ListLink<T>) x);
	}
	
	public Element<T> minimum() {
		return list.getHead();
	}
	
	public Element<T> maximum() {
		return list.getTail();
	}
	
	public Element<T> successor(Element<T> x) {
		return ((ListLink<T>) x).getNext();
	}
	
	public Element<T> predecessor(Element<T> x) {
		return ((ListLink<T>) x).getPrev();

	}
}
