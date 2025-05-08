/**
 * @param <T> The type of the satellite data of the elements in the data structure.
 */
public class MyFirstDataStructure<T> {
	MyAVLTree<T> mainTree;
	MyAVLTree<T> orderTree;
	int order;
	Element<T> firstElement;
	Element<T> lastElement;
	Element<T> maximum;


	public MyFirstDataStructure(int N) {
		this.mainTree = new MyAVLTree<>();
		this.orderTree = new MyAVLTree<>();
		this.order = 0;
	}
	
	public void insert(Element<T> x) {
		TreeNode<T> newNode = new TreeNode<>(x,order);
		TreeNode<T> orderNode = new TreeNode<>(order);
		if (mainTree.root() == null)
			firstElement = newNode;
		mainTree.insert(newNode);
		orderTree.insert(orderNode);
		lastElement = newNode;
		if (maximum == null || x.key() > maximum.key()) {
			maximum = newNode;
		}
		order++;

	}
	
	public void findAndRemove(int k) {
	    TreeNode<T> toRemove= mainTree.search(k);
		if (toRemove != null) {
			int toRemoveOrder = toRemove.getOrder();
			TreeNode<T> orderNode = orderTree.search(toRemoveOrder);
			mainTree.delete(toRemove);
			orderTree.delete(orderNode);
			if (firstElement == toRemove) {
				firstElement = mainTree.searchOrder(orderTree.getMin(orderTree.root()).key());
			}
			if (lastElement == toRemove) {
				lastElement = mainTree.searchOrder(orderTree.maxValueNode(orderTree.root()).key());
			}
			if (toRemove == maximum) {
				maximum = mainTree.maxValueNode(mainTree.root());
			}
		}
	}

	public Element<T> maximum() {
		return maximum;
	}

	public Element<T> first() {
		return firstElement;
	}

	public Element<T> last() {
		return lastElement;
	}

}
