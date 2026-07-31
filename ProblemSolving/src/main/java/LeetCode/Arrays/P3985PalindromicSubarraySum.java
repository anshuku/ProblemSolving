package LeetCode.Arrays;

/*
 * P3985. Palindromic Subarray Sum - Hard
 * 
 * You are given an integer array nums.
 * 
 * Return the maximum possible sum of a subarray of nums that is a palindrome.
 * 
 * Constraints:
 * > 1 <= nums.length <= 10^5
 * > 1 <= nums[i] <= 10​​​​​​​^9
 * 
 * Approach - Manacher + Prefix Sum, Prefix Sum + Binary Search + Hashing
 */
public class P3985PalindromicSubarraySum {

	static final long MOD1 = 1_000_000_007l, MOD2 = 998_244_353l;
	static final long BASE1 = 113, BASE2 = 117;

	public static void main(String[] args) {
//		int[] nums = { 10, 10 };

		int[] nums = { 7, 1, 2, 1, 7, 3, 4, 3, 4 };

//		int[] nums = { 1, 2, 3, 4, 5 };

//		int[] nums = { 1000 };

//		int[] nums = { 1, 2, 4, 2, 1, 13 };

//		int[] nums = { 2, 4, 4, 2, 1, 13 };

		long maxPalindromeSumManacher = getSumManacher(nums);
		System.out.println("Manacher: The maximum sum of subarray that is a palindrome: " + maxPalindromeSumManacher);

		long maxPalindromeSumBinarySearch = getSumBinarySearch(nums);
		System.out.println(
				"Binary Search: The maximum sum of subarray that is a palindrome: " + maxPalindromeSumBinarySearch);

		long maxPalindromeSumExpandCenters = getSumExpandCenters(nums);
		System.out.println(
				"Expand Centers: The maximum sum of subarray that is a palindrome: " + maxPalindromeSumExpandCenters);
	}

	// Manacher's Algorithm adapted for arrays - Sum version
	// It processes all centers in amortized O(n). Manacher's algo computes, for
	// every center simultaneously, the radius of the longest palindrome around it,
	// in O(n) total (not O(n) per center). Since all values are positive, the
	// longest palindrome at a center is always the one with the maximum sum at that
	// center (adding more matching elements only increases the sum, hence we don't
	// need to calculate max sum in cases where there are -ve elements). So we just
	// need the longest palindrome radius per center, then read off its sum via a
	// prefix sum array.
	// To handle the odd and even length palindromes uniformly, we insert
	// "separator" slots between elements (here 0, it's like # between characters in
	// the string version of Manacher's). Since, array values can be arbitrary
	// integers (cases where all integers are possible), we can't pick a sentinel
	// value that's guaranteed distinct - instead we track separator positions by
	// parity of index(even or odd) and compare by "type"(separator vs. real).
	// Explanation:
	// Transformed array (conceptually): For nums = [1, 2, 1], imagine T = [S, 1, S,
	// 2, S, 1, S] (indices 0-6) where S is a separator. Even indices are
	// separators, odd indices hold nums[(k-1)/2]. This trick means odd-length
	// palindromes in nums correspond to palindromes centered on a real element (odd
	// index) in T, and even-length palindromes correspond to palindromes centered
	// on a separator(even index) in T - one algorithm handles both cases.
	// prefix array: prefix[k] = sum of T[0..k-1], treating separator slots as value
	// 0. This lets us get the sum of any palindromic window in T for free -
	// separators just don't contribute.
	// equal(a, b): Since we don't have a real sentinel value, we simulate the
	// separator by checking index parity. 2 separator slots are always considered
	// equal to each other (this is what allows the palindrome to "grow"
	// symmetrically across the gap for even length matches). 2 real slots are equal
	// only if the underlysing nums values match. A separator can never equal a real
	// slot.
	// The Manacher loop itself (identical to classic string version):
	// > P[i] will hold the max radius of a palindrome in T centred at i.
	// > (center, right) track the rightmost palindrome found so far - right is the
	// furthest index that's provably still inside some palindrome. center is where
	// that palindrome is centered.
	// > Mirror trick: If i < right, the i is inside the current rightmost
	// palindrome, so it has a mirror image mirror = 2*center - i on the other side.
	// Whatever palindrome radius we already know at mirror gives us a guaranteed
	// minimum radius at i too (bounded by how far right extends, since we can't be
	// sure about anything past right yet) - this is what helps preventing the redo
	// which makes the algorithm linear. We've only verifed (via direct comparision)
	// that everything strictly between center and right mirrors correctly. We know
	// nothing yet about what lies beyond right - those elements haven't been
	// compared to anything.
	// P[mirror] alone can be wrong: When i < right, i sits inside this known
	// palindrome, so it has a mirror point, mirror = 2*center - i on the left side.
	// Because the region around center is a palindrome (out to right), whatever
	// pattern exists around mirror is reflected around i too - but only as far as
	// the reflection stays inside the [left, right] window we've already confirmed.
	// If P[mirror] is small enough that mirror's own palindrome stays entirely
	// within the big palindrome's boundaries, then yes - i's palindrome is
	// guaranteed to be exactly P[mirror] too, no extra checking needed.
	// But if P[mirror] is large such that mirror's palindrome pokes out past the
	// left edge of the big palindrome (index center - (right - center), i.e., past
	// what's mirrored by right on the right side) - then reflecting it around i
	// would claim i's palindrome extends past right. And we've no evidence for
	// that, as nothing past right has been compared yet. It may or may not be true.
	// So instead, we cap it: P[i] = min(right - i, P[mirror])
	// right - i is precisely how far i's palindrome can be guaranteed to extend
	// based on known symmetric region, without stepping outside [left, right]. This
	// gives us a safe lower bound, not necessarily the true answer.
	// It's safe to try expanding further: The code doesn't just stop - right after
	// setting P[i], it runs further expansion for P[i] via while loop. This picks
	// up from the guaranteed floor and tries to expand past right using real
	// element-by-element comparisions - because past right, we truly don't know
	// anything, so it has to be checked explicitly. If it does successfully expand
	// past right, center/right gets updated.
	// This keeps the algorithm at O(n), capping keeps this linear: the expansion
	// loop only ever runs on the portion of the palindrome radius that goes beyond
	// right, and right is monotonically non-decreasing. So the total number of
	// "extra" comparision steps across all i is bounded by how far right can move,
	// i.e., O(n) total - not O(n) per index.
	// P[mirror] tells one what's true about the mirror's own local neighborhood,
	// but that neighborhood might reach further than what's confirmed around i.
	// right - i is the boundary of what's confirmed. Taking the min means "trust
	// the mirror only upto the edge of verified territory - anything beyond that
	// has to be earneed by direct comparision".
	// > Expansion: Starting from that guranteed minimum, we try to expand further
	// by directly comparing symmetric positions with equal(...).
	// > Update center / right: If this palindrome reaches further right than any
	// before, it becomes the new reference.
	// > Sum lookup: Once P[i] is finalized for this center, prefix[i + P[i] + 1] -
	// prefix[i - P[i]] gives the sum of the corresponding palindromic window
	// directly - no need to even map back to original array indices.
	// Why prefix[i - P[i]], not prefix[i - P[i] - 1]:
	// The palindrome centered at i with radius P[i] spans transformed-array indices
	// (i - P[i], i + P[i]) (inclusive). That's the range we want the sum of.
	// Standard prefix-sum convention: prefix[k] = sum of T[0...k-1]. So sum of
	// T[l..r] = prefix[r+1] - prefix[l]. Here, l = i - P[i], r = i + P[i]. Plug in:
	// * prefix[r+1] = prefix[i+P[i]+1], * prefix[l] = prefix[i - P[i]]
	// This is the formula used. i - P[i] - 1 is the first index outside the
	// palindrome (1 past the left edge) - that's the boundary the expansion loop
	// needs to check (equal(a,b) compares the 2 elements just outside the current
	// radius). But for summing, prefix-sum subtraction needs the start of the
	// range, not one-before-the-start - those are different quantities that look
	// similar because of the +-1 pattern in Manacher's but they're used for
	// different purposes.
	// Why prefix has length m + 1, prefix[k] is defined as "sum of 1st k elements"
	// - so prefix[0] = 0 means sum of 0 elements or empty prefix. prefix[1] =
	// T[0],... prefix[m] = sum of all m elements. That's m + 1 values total (0
	// through m), hence array size m + 1.
	// This is what makes prefix[r+1] - prefix[l] always valid: even when r = m - 1
	// (the very last index), we need prefix[m], which requires the array to have
	// size m + 1. Without the extra slot, prefix[i + P[i] + 1] would go out of
	// bounds when a palindrome reaches the last index of T.
	// Time complexity - O(n), as the inner while expansion only ever increases
	// right, and right only moves forward across the whole outer loop, so total
	// expansion steps are bounded by O(m) = O(n)
	// Space complexity - O(n) for prefix and palindrome radius arrays.
	private static long getSumManacher(int[] nums) {
		int n = nums.length;
		int m = 2 * n + 1;

		long[] prefix = new long[m + 1]; // Transformed length: sep, x0, sep, x1, sep, x2, ..., xn-1, sep

		// Prefix sums over the transformed array (separators contribute 0)
		for (int i = 0; i < m; i++) {
			int val = i % 2 == 0 ? 0 : nums[(i - 1) / 2];
			prefix[i + 1] = prefix[i] + val;
		}

		int center = 0, right = 0;

		// P[i] = Palindrome radius in transformed array centred at i.
		int[] P = new int[m];

		long maxSum = 0;

		for (int i = 0; i < m; i++) {
			// Update i's palindrome radius based on current right value
			if (i < right) {
				int mirror = 2 * center - i; // it's the mirror inside the palindromic circle.
				P[i] = Math.min(right - i, P[mirror]); // right-i caps the boundary in case P[mirror] extends right
			}

			// Expand from centers
			while (i - P[i] - 1 >= 0 && i + P[i] + 1 < m && isEquals(i - P[i] - 1, i + P[i] + 1, nums)) {
				P[i]++;
			}

			// Update the right and center in case the current i has a bigger palindrome.
			if (i + P[i] > right) {
				center = i;
				right = i + P[i];
			}

			// prefix[k] = sum of T[0...k-1], hence we use sum = prefix[r+1] - prefix[l]
			// r = i+P[i], l = i-P[i] | prefix[i+P[i]+1] gives sum till i+P[i] which we
			// want, prefix[i-P[i]] gives sum till i-P[i]-1 which we want, basically we
			// add 1 to r and l remains as it is for subtraction, r = i+P[i] 1, l = i-P[i].
			long sum = prefix[i + P[i] + 1] - prefix[i - P[i]];
			maxSum = Math.max(maxSum, sum);
		}

		return maxSum;
	}

	private static boolean isEquals(int i, int j, int[] nums) {
		if (i % 2 == 0 && j % 2 == 0) { // Both are separators: always equal.
			return true;
		}
		if (i % 2 != 0 && j % 2 != 0) { // Both real: compare values
			return nums[(i - 1) / 2] == nums[(j - 1) / 2];
		}
		// One separator, another real: never equal
		return false;
	}

	static long[] prefixSum;
	static long[] pow1, pow2;

	static long[] H1, H2;
	static long[] RH1, RH2;

	// Binary Search + Hashing + Prefix Sum
	// We use polynomial rolling hashes to check "is nums [l..r] a palindrome?" in
	// O(1). Then exploit a monotonicity property: for a fixed center, if radius R
	// gives a palindrome, then radius R - 1 also gives a palindrome (removing the 2
	// outer matching elements from a palindrome always leaves a smaller
	// palindrome). This lets one, binary search the max radius per center in
	// O(logn), giving O(nlogn) overall.
	// Explanation:
	// Building the hashes:
	// > H1, H2 are prefix polynomial hashes of nums, computed with 2 different
	// (base, mod) pairs - using 2 independent hashes ("double hashing") drastically
	// reduces the chance of a collision passing an incorrect palindrome check,
	// which matters since a single hash on on adversarial LeetCode test data could
	// theoretically collide. It can be solved with just 1 hash, H1 although.
	// > RH1, RH2 are the same, but computed on reverse, the reversed array.
	// > pow1, pow2 are precomputed powers of the bases, needed to "shift" hash
	// prefixes when extracting a substring hash.
	// Why we compare against the reversed array?
	// A subarray nums[l..r] is a palindrome exactly when it reads the same forwards
	// and backwards, i.e., nums[l..r] equals reverse(nums[l..r]). Since rev[i] =
	// nums[n-1-i], the segment reverse(nums[l..r]) is exactly rev[n-1-r...n-1-l].
	// So, isPalindrome(l, r) <=> hash(nums[l...r]) == hash(rev[n-1-r...n-1-l])
	// That's what isPalindrome computes: it gets the hash of nums[l...r] from the
	// forward hash arrays, and the hash of the mirrored segment from the reversed
	// hash arrays, and compares both (double-checked with both hash functions).
	// getHash(h, pow, mod, l, r): standard technique to pull a substring hash out
	// of a prefix-hash array in O(1): h[r+1] - h[l] * base ^ (r-l+1), taken mod mod
	// (with a fix-up for negative results in Java, since % can return negative
	// values).
	// Binary Search per center (why it's valid): For a fixed center, as radius
	// increases from 0 upto some max, the palindrome property is monotonic: if
	// nums[i-R..i+R] is a palindrome, then nums[i-R+1..i+R-1] must also be a
	// palindrome (one just removes the outer matched pair, which doesn't break
	// inner symmetry). So the "yes" radii form a prefxi [0, 1,..., R_max] - a
	// classic binary search shape. We binary search for the largest radius that
	// still satisfies isPalindrome.
	// > Odd-length case: center is a single index i; radius 0 (single element) is
	// always trivially valid, so best starts at 0 and we search upward.
	// > Even-length case: center sits between i and i+1; radius 0 here means just
	// checking the pair nums[i], nums[i+1]. If that pair isn't even equal, there's
	// no even-length palindrome at this center at all, so we skip it (continue).
	// Otherwise we binary search from there.
	// Getting the sum: Once we know the best radius for a center, prefixSum[...] -
	// prefixSum[...] gives the sum of that specific palindromic window in O(1),
	// same as a normal subarray-sum-via-prefix-sum trick.
	// In odd-length palindromes, we use maxR = min(i, n - i - 1) to enforce array
	// bounds for radius, r can expand towards left by, i - r >= 0, r <= i. r can
	// expand towards right by, i + r <= n - 1 or r <= n - 1 - 1. So, radius can't
	// exceed either of these 2 limits so we use min of both.
	// We guarantee radius to be odd, with the structure of the window [i-r, i+r],
	// isPalindrome(i - r, i + r), this has array of length 2*r + 1 - odd, for any
	// valud r, including r = 0, (single element, length = 1). Since i is a single
	// fixed index (not a gap between 2 indices), every window built around it is
	// automatically odd-length, regardless of whatever radius is picked.
	// Why, start = 0, not i? start / end are bounds on the radius r, not on array
	// indices - easy to conflate (combine wrongly) since i also happens to be a
	// valid index. Radus r = 0, is always a valid palindrome (a single element
	// trivially equals itself), so it's the correct starting point for the search.
	// We're binary-searching over "how large can r get", starting from the smallest
	// possible radius 0 upto maxR. Using start = i would be conflating radius with
	// index and is unrelated to what's being searched.
	// For even-length palindromes, the outer loop runs till i < n - 1: center uses
	// both i and i + 1, so i + 1 must be a valid index -> i <= n - 2 -> loop
	// condition i < n - 1.
	// We use maxR = min(i, n - i - 2) to enforce array bounds. Left expansion
	// needs, i - r >= 0 or r <= i. Right expansion needs i + 1 + r <= n - 1 or r <=
	// n - 2 - i. We take min to get r's boundary.
	// The even window shape uses structure isPalindrome(i - mid, i + 1 + mid). The
	// even window [l, r] = [i - mid, i + 1 + mid], matches the even window shape.
	// Here, the center sits between i and i + 1; radius r gives window [i-r,i+r+1],
	// length 2*r + 2 - always even, for any r >= 0 (including r = 0, giving just
	// the pair [i, i+1]).
	// We use start = 1: r = 0 (the base pair nums[i], nums[i+1]) is checked before
	// the binary search starts: if(!isPalindrome(i, i+1)) continue;
	// If this passes, r = 0 is already confirmed valid, best is initialized to 0.
	// So the binary search only needs to look for something larger - hence starts
	// at start = 1. If we started at start = 0, we'd just be re-verifying r = 0,
	// which is redundant (we already know it).
	// Sum formula: window is [i - best, i + 1 + best]. Using sum(l..r) = prefix[r +
	// 1] - prefix[l]: l = i - best -> prefix[i - best], r = i + 1 + best ->
	// prefix[i + 1 + best + 1]. So prefix[i + 1 + best + 1] - prefix[i - best] is
	// just that formula applied directly - consistent with how sums are extracted.
	// Rolling Hash: We define h[k] = hash of nums[0...k-1] as a polynomial:
	// h[k] = nums[0]*base^(k-1) + nums[1]*base^(k-2) +...+ nums[k-1]*base^0 (mod)
	// Each prefix hash is built incrementally: h[k+1] = h[k]*base + nums[k].
	// This is how one can evaluate a polynomial digit-by-digit (like turning a
	// decimal string into a number).
	// Why we need to "shift" h[l]: we want the hash of just nums[l...r]
	// h[l] = nums[0]*base^(l-1) + nums[1]*base^(l-2) +..+ nums[l-1]*base^0
	// hash(nums[l..r]) = nums[l]*base^(r-l) + nums[l+1]*base^(r-l-1) + ... +
	// nums[r]*base^0.
	// h[r+1] = nums[0]*base^(r) + ... + nums[l-1]*base^(r-l+1) | similar to h[l]
	// + nums[l]*base^(r-l) + ... + nums[r]*base^0 | matches hash(nums[l..r])
	// So h[r+1] = (hash of unwanted prefix nums[0....l-1], but with extra factors
	// of base included) + (the part we want).
	// We need to cancel out that unwanted first part. Notice h[l] = the same prefix
	// nums[0..l-1], but with smaller powers of base (missing exactly r-l+1 extra
	// factors of base compared to how it's there inside h[r+1]).
	// So if we multiply h[l] by base^(r-l+1), we boost every term's exponent by
	// exactly r-l+1, making it match term-for-term with the part inside h[r+1].
	// h[l] * base^(r-l+1) = nums[0]*base^r +...+ nums[l-1]*base^(r-l+1)
	// It's identical to unwanted prefix portion of h[r+1]. We subtract to cancel:
	// h[r+1] - h[l]*base^(r-l+1) = hash(nums[l..r])
	// It's the formula and not arbitrary - it falls directly out of matching
	// exponents so the unwanted prefix terms cancel.
	// Why we took mod of the product h[l]*base^(r-l+1) first before subtracting:
	// h[l]*base^(r-l+1) can be a huge number (upto mod*mod before reduction) -
	// computing it without taking mod may overflow long in Java. So, we reduce that
	// product via mod before subtracting, to keep every intermediate value safely
	// within [0, mod). Since modular arithmetic is compatible with subtraction
	// ((a-b) mod m is valid regardless of when b is reduced), it's mathematically
	// fine to mode the product first, then subtract, then mod the final result
	// again.
	// Why we need negative fix-up, h[r+1] and h[l]*base^(r-l+1) % mod are each in
	// [0, mod), but subtracting 2 values can go negative (3 - 7 = -4). Java's %
	// operator returns a result with the same sign as dividend, so -4 % mod stays
	// negative - unlike Python's %, which auto-corrects. So we manually add mod
	// back if the result is -ve, restoring to the proper [0,mod) range.
	// Time complexity - O(nlogn), n center * O(logn) binary search steps * O(1) per
	// hash check = O(nlogn) time, O(n) space. This comfortably fits n = 10^5.
	private static long getSumBinarySearch(int[] nums) {
		int n = nums.length;

		prefixSum = new long[n + 1];

		for (int i = 0; i < n; i++) {
			prefixSum[i + 1] = prefixSum[i] + nums[i];
		}

		pow1 = new long[n + 1];
		pow2 = new long[n + 1];

		pow1[0] = pow2[0] = 1;

		// pow[0] = base^0 = 1
		// pow[1] = pow[0]*base^1 = base
		// pow[2] = pow[1]*base = base^2
		// pow[3] = pow[2]*base = base^3
		// pow[k] = base^k
		for (int i = 1; i <= n; i++) {
			pow1[i] = pow1[i - 1] * BASE1 % MOD1;
			pow2[i] = pow2[i - 1] * BASE2 % MOD2;
		}

		int[] reverse = new int[n];

		for (int i = 0; i < n; i++) {
			reverse[i] = nums[n - 1 - i];
		}

		// Prefix polynomial hashes
		H1 = buildHash(nums, BASE1, MOD1);
		H2 = buildHash(nums, BASE2, MOD2);

		RH1 = buildHash(reverse, BASE1, MOD1);
		RH2 = buildHash(reverse, BASE2, MOD2);

		long maxSum = 0;

		// odd-length palindromes, center at i | [i - r, i + r]
		// radius is found via start, end = maxR and best with help of binary search.
		// Odd length palindrome comes from the structure of the window [i - r, i + r].
		// This window has length 2*r + 1 - odd (even when r = 0)
		for (int i = 0; i < n; i++) {
			// To get possible array bounds of radius, as i - r >= 0 and i + r <= n - 1
			// or r <= i and r <= n - 1 - i so we take the min of both.
			int maxR = Math.min(i, n - 1 - i);

			// We cans start r from 0 to maxR, as we have odd length palindrome.
			int start = 0, end = maxR;
			int best = 0;

			while (start <= end) {
				int mid = start + (end - start) / 2;

				if (isPalindrome(i - mid, i + mid, n)) {
					best = mid;
					start = mid + 1;
				} else {
					end = mid - 1;
				}
			}

			// prefix[k] = sum of T[0...k-1], hence we use sum = prefix[r+1] - prefix[l]
			// Also, best + 1 = start
//			long sum = prefixSum[i + best + 1] - prefixSum[i - best];
			long sum = prefixSum[i + start] - prefixSum[i - start + 1];
			maxSum = Math.max(maxSum, sum);
		}

		// Even-length palindromes, center between i and i + 1 | [i - r, i + 1 + r]
		// radius is found via start, end = maxR and best with help of binary search.
		// Even length palindrome comes from the window [i - r, i + r + 1].
		// This window has length 2*r + 2 - even (even when r = 0)
		for (int i = 0; i < n - 1; i++) {
			// For even-length palindromes, it's important to check adjacent element equal
			// before binary search as it may lead to sums for elements which aren't
			// palindromes.
			if (!isPalindrome(i, i + 1, n)) {
				continue;
			}
			// To get possible bounds of radius as i - r >= 0 and i + r + 1 <= n - 1
			int maxR = Math.min(i, n - 2 - i);
			// We can use start = 0 as well, but it's redundant as we already checked it.
			int start = 1, end = maxR;
			int best = 0;

			while (start <= end) {
				int mid = start + (end - start) / 2;

				if (isPalindrome(i - mid, i + mid + 1, n)) {
					best = mid;
					start = mid + 1;
				} else {
					end = mid - 1;
				}
			}

			// prefix[k] = sum of T[0...k-1], hence we use sum = prefix[r+1] - prefix[l]
			// Also, best + 1 = start
			long sum = prefixSum[i + 1 + best + 1] - prefixSum[i - best];
//			long sum = prefixSum[i + start + 1] - prefixSum[i - start + 1];
			maxSum = Math.max(maxSum, sum);
		}

		return maxSum;
	}

	// h[k] = hash of nums[0..k-1] as a polynomial.
	// hash[3] = nums[0]*base^2 + nums[1]*base + nums[2]*base^0 (mod)
	// hash[k] = nums[0]*base^(k-1) + nums[1]*base^(k-2) +..+ nums[k-1]*base^0 (mod)
	private static long[] buildHash(int[] nums, long base, long mod) {
		int n = nums.length;
		long[] hash = new long[n + 1];

		for (int i = 0; i < n; i++) {
			hash[i + 1] = (hash[i] * base + nums[i]) % mod;
		}
		return hash;
	}

	private static boolean isPalindrome(int l, int r, int n) {
		long hf1 = getHash(H1, pow1, MOD1, l, r);
		long hf2 = getHash(H2, pow2, MOD2, l, r);

		// Corresponding window in reversed array.
		int rl = n - 1 - r;
		int rr = n - 1 - l;

		long hb1 = getHash(RH1, pow1, MOD1, rl, rr);
		long hb2 = getHash(RH2, pow2, MOD2, rl, rr);

		return hf1 == hb1 && hf2 == hb2;
	}

	// Standard technique: Pull a substring hash out of a prefix hash in O(1):
	// h[r+1] - h[l] * base^(r-l+1)
	// h[k] = hash of nums[0...k-1],
	// hash[k] = nums[0]*base^(k-1) + nums[1]*base^(k-2) +..+ nums[k-1]*base^0 (mod)
	// hash[l] = hash(nums[0..l-1]), hash[r+1] = hash(nums[0..r])
	// We need to get hash[l...r]
	// hash(nums[l..r]) = nums[l]*base^(r-l) + nums[l+1]*base^(r-l-1) +...+
	// nums[r]*base^0
	// hash[l] = nums[0]*base^(l-1) + nums[1]*base^(l-2) +..+ nums[l-1]*base^0 (mod)
	// hash[r+1] = nums[0]*base^(r) + ... + nums[l-1]*base^(r-l+1) | ~nums[0...l-1]
	// + nums[l]*base^(r-l) + ... + nums[r]*base^0 | this part is hash(nums[l...r])
	// We need to remove the first part. It can be done if we subtract
	// hash(nums[0..l])*base^(r-l+1) from hash[r+1].
	// h[l]*base^(r-l+1) = nums[0]*base^r + ... + num[l-1]*base^(r-l+1).
	// h[r+1] - h[l]*base^(r-l+1) = hash(nums[l...r])
	// We added + mod before final mod as the subtracting may lead to negative
	// values and + mod restores the hash to proper [0,mod) range.
	private static long getHash(long[] h, long[] pow, long mod, int l, int r) {
		long result = (h[r + 1] - h[l] * pow[r - l + 1] % mod + mod) % mod;
		return result;
	}

	// Expanding the Centers - TLE
	// The helper getPalindromeSum does 2 things per center: an expansion loop
	// (while matching) and then a separate summation loop over the matched range.
	// In the worst case (e.g., an array of all equal values), each center's
	// palindrome can span almost the whole array, so both loops are O(n) per
	// center. With n centers, that's O(n^2) - 10^10 operations for n = 10^5.
	// As per constraints the time taken is >= 10^5*10^5 or 10^10 which is >>
	// allowed 10^8.
	public static long getSumExpandCenters(int[] nums) {
		int n = nums.length;

		long maxSum = 0;

		for (int i = 0; i < n; i++) {
			long maxOddSum = getPalindromeSum(i, i, nums);
			long maxEvenSum = getPalindromeSum(i, i + 1, nums);

			maxSum = Math.max(maxSum, Math.max(maxOddSum, maxEvenSum));
		}

		return maxSum;
	}

	private static long getPalindromeSum(int i, int j, int[] nums) {
		int n = nums.length;

		if (j < n && nums[i] != nums[j]) {
			return 0;
		}

		long sum = 0;

		while (i >= 0 && j < n && nums[i] == nums[j]) {
			i--;
			j++;
		}

		for (int a = i + 1; a <= j - 1; a++) {
			sum += nums[a];
		}

		return sum;
	}

}
