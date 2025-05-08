public class MySecondDataStructure {
	int maxCapacity;
	MyArray<Product> array;

	int[] qualityCount;
	int[] qualitySum;
	int[] raisPrice;

	MyArray<Product>[] qualityBuckets;
	ArrayElement<Product>[] qualityMax; // Track max per quality

	ArrayElement<Product> maxPriceElement;

	public MySecondDataStructure(int N) {
		this.maxCapacity = N;
		this.array = new MyArray<>(maxCapacity);
		this.qualityCount = new int[6];
		this.qualitySum = new int[1];
		this.raisPrice = new int[6];

		this.qualityBuckets = new MyArray[6];
		this.qualityMax = new ArrayElement[6];
		for (int i = 0; i < 6; i++) {
			this.qualityBuckets[i] = new MyArray<>(maxCapacity);
		}
	}

	public void insert(Product product) {
		ArrayElement<Product> element = new ArrayElement<>(product.id(), product);
		array.insert(element);

		int q = product.quality();
		qualityCount[q]++;
		qualitySum[0] += q;
		qualityBuckets[q].insert(element);

		if (qualityMax[q] == null ||
				product.price() > qualityMax[q].satelliteData().price()) {
			qualityMax[q] = element;
		}

		if (maxPriceElement == null ||
				product.price() + raisPrice[q] > maxPriceElement.satelliteData().price() + raisPrice[maxPriceElement.satelliteData().quality()]) {
			maxPriceElement = element;
		}
	}

	public void findAndRemove(int id) {
		ArrayElement<Product> element = array.search(id);
		if (element == null) return;

		Product product = element.satelliteData();
		int q = product.quality();
		qualityCount[q]--;
		qualitySum[0] -= q;
		qualityBuckets[q].delete(element);

		if (element == qualityMax[q]) {
			qualityMax[q] = null;
			for (int i = 0; i < qualityBuckets[q].size(); i++) {
				ArrayElement<Product> e = qualityBuckets[q].get(i);
				if (qualityMax[q] == null ||
						e.satelliteData().price() > qualityMax[q].satelliteData().price()) {
					qualityMax[q] = e;
				}
			}
		}

		if (element == maxPriceElement) {
			maxPriceElement = null;
			for (int i = 0; i < 6; i++) {
				if (qualityMax[i] != null) {
					int effectivePrice = qualityMax[i].satelliteData().price() + raisPrice[i];
					if (maxPriceElement == null ||
							effectivePrice > maxPriceElement.satelliteData().price() + raisPrice[maxPriceElement.satelliteData().quality()]) {
						maxPriceElement = qualityMax[i];
					}
				}
			}
		}

		array.delete(element);
	}

	public int medianQuality() {
		int total = array.size();
		int mid = (total + 1) / 2;
		int count = 0;

		for (int q = 0; q <= 5; q++) {
			count += qualityCount[q];
			if (count >= mid) {
				return q;
			}
		}
		return -1;
	}

	public double avgQuality() {
		if (array.size() == 0) return -1;
		return (double) qualitySum[0] / array.size();
	}

	public void raisePrice(int raise, int quality) {
		raisPrice[quality] += raise;

		if (qualityMax[quality] != null) {
			int candidatePrice = qualityMax[quality].satelliteData().price() + raisPrice[quality];
			if (maxPriceElement == null ||
					candidatePrice > maxPriceElement.satelliteData().price() + raisPrice[maxPriceElement.satelliteData().quality()]) {
				maxPriceElement = qualityMax[quality];
			}
		}
	}

	public Product mostExpensive() {
		if (maxPriceElement != null) {
			return maxPriceElement.satelliteData();
		} else {
			return null;
		}
	}

	public int getRealPrice(Product product) {
		return product.price() + raisPrice[product.quality()];
	}
}
