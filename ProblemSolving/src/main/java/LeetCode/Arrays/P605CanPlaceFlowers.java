package LeetCode.Arrays;

public class P605CanPlaceFlowers {

	public static void main(String[] args) {
		int[] flowerbed = { 1, 0, 0, 0, 0, 1 };
		int n = 1;
		boolean val = canPlaceFlowers(flowerbed, n);
		// boolean val = canPlaceFlowers(flowerbed, n);
		System.out.print(n + " Flowers can be placed - " + val);

	}

	public static boolean canPlaceFlowers(int[] flowerbed, int n) {
		int counter = 0;
		for (int i = 0; i < flowerbed.length; i++) {
			if (flowerbed[i] == 0) {
				boolean emptyLeft = (i == 0) || (flowerbed[i - 1] == 0);
				boolean emptyRight = (i == flowerbed.length - 1) || (flowerbed[i + 1] == 0);
				if (emptyLeft && emptyRight) {
					counter++;
					flowerbed[i] = 1;
					if (counter == n) {
						return true;
					}
				}
			}
		}
		return counter >= n;
	}

	public static boolean canPlaceFlowersAlt(int[] flowerbed, int n) {
		int l = flowerbed.length;
		for (int i = 0; i < l; i += 2) {
			if (flowerbed[i] == 0) {
				if (i == l - 1 || flowerbed[i] == flowerbed[i + 1]) {
					n--;
				} else {
					i++;
				}
			}
		}
		return n <= 0;
	}

}
