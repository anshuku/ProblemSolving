package LeetCode.Math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * P3518. Smallest Palindromic Rearrangement II - Hard
 * 
 * You are given a palindromic string s and an integer k.
 * 
 * Return the k-th lexicographically smallest palindromic permutation of s. If 
 * there are fewer than k distinct palindromic permutations, return an empty string.
 * 
 * Note: Different rearrangements that yield the same palindromic 
 * string are considered identical and are counted once.
 * 
 * Approach - Maths, Combinatorics, Greedy, Backtracking
 */
public class P3518SmallestPalindromicRearrangementII {

	// rem!/c1!*c2!
	// 5!/3!*2!
	// 12!/3!*4!*5!
	// 12!/3!
	// 8!/4!
	// 5!/5!
	// 12!*8!*5!/3!*4!*5!
	// ncr = n!/(n-r)!*r! = n*(n-1)*...*(n-r+1)/r! | 0 -> r-1
	public static void main(String[] args) {
//		String s = "abba"; // baab
//		int k = 2;

//		String s = "aa"; // empty string
//		int k = 2;

//		String s = "bacab"; // abcba
//		int k = 1;

		String s = "tqklgxtxippixtxglkqt"; // kxlxtigpqttqpgitxlxk
		int k = 259465;

		String kthPalindromeMaths = smallestPalindromeMaths(s, k);
		System.out.println("Maths: The kth lexicographically smallest palindrome of s is: " + kthPalindromeMaths);

		String kthPalindromeBF = smallestPalindromeBruteForce(s, k);
		System.out.println("Brute Force: The kth lexicographically smallest palindrome of s is: " + kthPalindromeBF);

	}

	// Combinatorital Maths + Trial and Error
	// We've to find the k-th lexicographically smallest palindromic permutation. We
	// need to construct an arbitrary lexicographically rank instead of only the
	// smallest one. The symmetry of a palindrome implies that the multisets of
	// characters on the left and right halves are completely determined once one
	// half is fixed. Therefore, we don't need to construct the entire palindrome
	// directly. Instead, we only need to consider the permutations of the left half
	// of the string.
	// The problem is transformed to: "Given a multiset (with repeating elements) of
	// characters, find its k-th lexicographically smallest permutation."
	// We solve this using a trial-and-error approach. At each position, we try
	// every available character in lexicograhpical order. Suppose the prefix
	// constructed so far is prefix, and we're currently trying character c. Let L
	// and R denote the lexicographical ranks of the smallest and largest
	// permutations that begin with prefix || c, respectively. There are 3 cases:
	// * If R >= k, then the desired permutation lies within this interval. Since
	// the characters are tried in lexicographical order, the current character must
	// be part of the answer. We append c to the prefix and continue constructing
	// the next position.
	// * If R < k, then all permutations beginning with this prefix come before the
	// desired one. We restore the character to the multiset, continue trying the
	// next candidate character, and update L=R + 1.(we changed the character, +1).
	// * If every possible character has been tried and still R < k, then fewer than
	// k permutations exist, so we return an empty string.
	// Initially, L = 1 as the lowest rank starts from 1. The remaining question is
	// how to compute R once the current prefix is fixed.
	// The Cantor expansion algorithm computes the lexicographical rank of a given
	// permutation. Here, we essentially perform the inverse process. Since Cantor
	// expansion is based on combinatorial counting, we use combinatorial maths to
	// compute how many permutations can be formed after fixing the current prefix.
	// Suppose the remaining number of positions is rem after fixing both the
	// current prefix and trial character c. Let the remaining multiset be W.
	// Assume the distinct characters in W are c1, c2, ..., c26, with frequencies
	// count[ci], where ∑count[ci] = rem.
	// The number of distinct permutations for these characters are:
	// P=rem!/(count[c1]!*count[c2]!*...*count[c26]!), or P = rem!/∏c∈W(count[c]!).
	// Therefore, R = L + P - 1.
	// Unfortunately, directly computing factorials is not practical because the
	// problem does not permit arbitrary-precision arithmetic or modular inverses.
	// Instead, we rewrite the multinomial coefficient as a product of ordinary
	// combinations
	// 1. Among the rem remaining positions, choose count[c1] positions for
	// character c1. This contributes C of count[c1] in rem.
	// 2. After placing all copies of c1, choose count[c2] positions from the
	// remaining positions. C of count[c2] in (rem - count[c1])
	// 3. We continue until every character has been placed.
	// By multiplication priniciple,
	// P = ∏ i = 1 to 26 C of count[ci] in (rem - ∑ j = 1 -> i-1 count[cj])
	// We still cannot compute combinations using factorials directly. Instead, we
	// evaluate each combination using its multiplicative formula:
	// C of m in n = ∏ i = 1 -> 26 (n-i+1)/i.
	// We use the summetry of combinations by replacing m with m = min(m, n - m)
	// which reduces the number of iterations.
	// During computation, res(i) denotes the value after the i-th iteration. Then:
	// res(i) = res * i - (n - i + 1)/i.
	// Since the product of any i consecutive integers is divisible by i!, we can
	// safely multiply first and divide afterward, ensuring that every intermediate
	// result remains an integer. Also, we never need an exact value once it exceeds
	// k. Therefore, we terminate the computation immediately whenever res(i) > k,
	// which avoids overflow while remaining sufficient for our comparison.
	// Using this combinatorial counting with trial-and-error construction, we can
	// determine each character of the left half and finally reconstruct the
	// palindrome.
	// Claude:
	// 1. Why only the 1st half matters:
	// Since s is guaranteed to be a palindrome, any rearrangement that is also a
	// palindrome is completely determined by its 1st half (2nd half is just the
	// mirror). If n is odd, the middle character of the rearranged palidnrome must
	// be whichever character has an odd count - that's forced, not a free choice.
	// So the algorithm only needs to find the k-th smallest arrangement of the left
	// half (partition = n/2 characters), built from half of each character's total
	// count. Since s is a palindrome, the first n/2 characters already contain
	// exactly half of each even count letter, so this bucket is exactly the
	// multiset available to permute freely.
	// 2. Greedy digit-by-digit construction ("k-th permutation with repeats")
	// This is the standard technique for finding the k-th smallest string built
	// from a multiset: fix positions one at a time, left to right, trying the
	// smallest available letter first at each position, and use combinatorics to
	// know how many completions start with that prefix without generating them.
	// * start tracks "the rank of the 1st arrangement we haven't skipped yet"
	// (1-indexed).
	// * For each candidate letter i at the current position, ways = number of
	// distinct ways to arrange the remaining bucket in the remaining slots.
	// * If k falls inside [start, start + ways - 1], letter i is correct for this
	// position - keep it and move on. ways - 1 as start includes the 1st
	// arrangement. Example: start=2 ways=7 [2,8] 2+7 > 8, k = 8.
	// Otherwise, that whole block of ways arrangements is skipped (start += ways)
	// and letter i + 1 is tried.
	// This is exactly the same idea as the classic "k-th permutation of 1..n"
	// problem, generalized to a multiset.
	// 3. Counting completions without overflow - permutations()
	// We can't just compute the multinomical coefficient rem!/(n1!*n2!*...*n26!)
	// directly with factorials, because rem can be upto 5000 and that number is
	// astronomically large - while we only ever care whether it exceeds k (<=
	// 10^6). So, the code computes it as a running product of binomial
	// coefficients, capping early.
	// The mathematical fact being used: the number of distinct arrangements of a
	// multiset equals the product, letter by letter, of "choose positions for this
	// letter out of the slots not yet assigned".
	// 4. Computing C(n, m) safely - combinations()
	// The standard multiplicative formula for C(n, m):
	// (n m​)= ∏ i = 1 -> m (n−i+1​)/i, computed incrementally so the running
	// product result is always an integer at each step (this is a well-known
	// property: after multiplying by (n-i+1) and dividing by i, the partial result
	// is always exactly C(n-m+i, i) and integer). Also note that it's result =
	// result * (n - i + 1) / i; and not result *= (n - i + 1) / i; as the former
	// involves multiplication first then division preventing any remainder drop. If
	// result ever exceeds k, the function returns k + 1 immediately - the caller
	// only ever needs to know "if this >= what's left of k," not the exact huge
	// value, so this keeps everything in the long range even fro n >= 10^4.
	// 5. Finishing up:
	// If at some position no letter i satisfies start + ways > k, nothing gets
	// appended and the loop silently produces a short string - this happens exactly
	// when k exceeds the total number of distinct palindromic permutations. That's
	// the "not enough rearrangements".
	// Finally append the fixed middle character if n is odd, then mirror the left
	// half onto the end to complete the palindrome.
	// How - multinomial coefficient -> product of binomial coefficients:
	// The multinomial coefficient: The number of distinct arrangements of a
	// multiset with n total items split into groups of size n1, n2, ..., n26 (1
	// group per letter) is: (n n1,n2,...,n26) = n! / (n1!*n2!*...*n26!)
	// Rewriting it as a product of ordinary C(*,*) terms. Think of building the
	// arrangement not by placing letters into an ordered sequence of positions
	// directly, but by choosing which positions get which letter, one letter at a
	// time:
	// 1. Out of the n total positions, choose which n1 of them gets letter 'a':
	// nCn1 ways.
	// 2. Out of the remaining (n-n1) positions, choose which n2 will hold 'b':
	// (n-n1)Cn2 ways. Here, no matter which specific set was chosen in stage 1,
	// there are always exactly n - n1 positions left, so this is always (n-n1)Cn2 -
	// same number on every branch.
	// 3. Out of the remaining (n-n1-n2) positions, choose which n3 hold letter 'c':
	// (n-n1-n2)Cn3 ways. Here, again it always gives (n-n1-n2)Cn3 choices
	// regardless of which specific positions were used up before.
	// 4. and so on through all 26 letters. %%
	// By the multiplication priniciple, total number of arrangements is product:
	// nCn1 * (n-n1)Cn2 * (n-n1-n2)Cn3 * ... * (n-n1-n2-...-n25)Cn26.
	// Reason for product = Number of Arrangements:
	// If a process happens in stages, and stages 2 always has the same number of
	// options no matter what is picked in stage 1, then:
	// total outcomes = (choices in stage1)*(choices in stage2)*...
	// Here, the actual available options can change from branch to branch (that's
	// fine), but the count of options must stay the same across every branch for
	// the multiplication to be valid.
	// The 2 sets are in bijection (function: One-to-One or Injective means no 2
	// items point to same target; Onto or Surjective means every item in target is
	// hit by at least 1 input and no target is left empty) or same in both ways.
	// This correspondence goes both directions: Forward and Backward
	// sequence of choices -> arrangement and arrangement -> sequence of choices.
	// Because every arrangement decodes back to exactly 1 sequence of
	// stage-choices, and every sequence of stage-choices encodes to exactly 1
	// arrangement, the 2 sets are in bijection. When 2 sets are in bijection, they
	// have the same size.
	// Counting sequences of choices (which is easy - just multiply the branching
	// factors) is therefore the same as counting arrangements (which is what we
	// actually wanted).
	// Continuing %%
	// At the end, every position has been assigned a letter exactly once (every
	// letter's full count gets placed, and positions never overlap since each stage
	// only picks from what's left), so each complete sequence of choices is a full
	// arrangement of the multiset - and by the same forward/backward argument as
	// above, this correspondence is a bijection.
	// So the product nCn1 * (n-n1)Cn2 * (n-n1-n2)Cn3 * ... * (n-n1-n2-...-n25)Cn26
	// isn't just numerically equal to the multinomial coefficient (which the
	// telescoping proves) - it's counting the same set of objects, just by
	// decomposing the act of "build 1 arrangement" into a sequence of smaller "pick
	// positions for this letter" decisions. That's the conceptual reason
	// multiplication is valid at all, independent of algebraic cancellation.
	// Why this equals the multinomial coefficent. Expand each binomial coefficient
	// by its factorial definition and the terms telescope:
	// nCn1 = n!/n1!*(n-n1)!, (n-n1)Cn2 = (n-n1)!/n2!*(n-n1-n2)!
	// Every "leftover positions" factorial in the denominator of 1 term - like
	// (n-n1)! - cancels exactly with the numerator of the next term. After all 26
	// multiplications, everything extra cancels except: n!/n1!*n2!*...*n26!
	// which is exactly the multinomial coefficient. So the two formulas are
	// provably identical - they're just 2 different ways of counting the same thing
	// (arranging all letters at once, vs. placing one letter group's positions at a
	// time).
	// Why the code prefers this form: Computing n! directly for n upto 5000 needs
	// big-integer arithmetic and is wasteful as we only care whether the count
	// exceeds k <= 10^6. The product of binomial coefficients form lets one compute
	// 1 small C(n, ni) at a time (each fits comfortably in a long, especially with
	// the early exit cap at k + 1), and one can stop multiplying the moment the
	// running product exceeds k - which is exactly what permutations() and
	// combinations() do. This turns an intractable factorial computation into a
	// sequence of cheap, boundedly-sized combinatorial computations.
	// Time complexity analysis of the Permutation Count:
	// First, consider the complexity of computing a single combination.
	// Each computation either terminates early or performs at most count[ci]
	// iterations. Suppose the loop executes x iterations, where x <= m. Using the
	// multiplicative expansion, nCx = n*(n-1)*...(n-x+1)/x*(x-1)*...1
	// = ∏ j = 0 -> x - 1 (n−j​)/(x-j). Since n >= 2m >= 2x, every term satisfies
	// (n-j)/(x-j) = (2x-j)/(x-j) = 1 + x/(x-j) >= 2.
	// Therefore, the intermediate result at least doubles in every iteration.
	// Because the computation stops as soon as the value exceeds k, the number of
	// iterations is bounded by O(logk). Hence, the complexity of computing a single
	// combination is O(min(count[ci], logk)).
	// Next, consider computing the entire permutation count P. Suppose the
	// combination computations execute x1, x2,..., xp iterations, respectively.
	// Their product grows by at least 2^x1*2^x2*...*2^xp = 2^∑xj. Since the
	// computation stops once the product exceeds k, 2^∑xj <= k, which implies
	// ∑xj <= logk. Therefore, the total work of all inner combination computations
	// is O(logk). On the other hand, the total number of iterations is also bounded
	// by the number of remaining characters, namely ∑xj <= rem, and rem is O(n).
	// Combining these 2 bounds, the total number of iterations performed by all
	// combination computations is O(min(n, logk)).
	// Finally, computing P also requires scanning the entire character set once,
	// contributing an additional O(σ) cost. Therefore, the total time required to
	// compute P is O(σ + min(n, logk)).
	// Complexity Analysis:
	// Let n be the length of the palindrome string s, and let σ denote the size of
	// the character set, which is 26 in this problem.
	// Claude Time complexity:
	// Bounding 1 permutations() call: O(σ + min(n, logk)). Inside permutations:
	// This has 2 distinct costs that add together (not multiply):
	// a) The bookkeeping cost - O(σ), Regardless of what happens, the for loop has
	// to at least look at each of the 26 buckets to check if (bucket[i] == 0)
	// continue. Even in the best case this scan costs O(σ). This is fixed overhead,
	// independent of the actual arithmetic.
	// b) The arithmetic cost - O(min(n, logk)), this is the costs of the
	// combinations() calls that actually do real work (nonzero buckets). Two
	// separate caps limit how much work this can be:
	// * Cap by n: combinations()'s own inner loop runs at most m = min(bucket[i],
	// rem - bucket[i]) times, and rem <= n. Summed across every bucket touched, the
	// total number of multiplicative steps can never exceed the total number of
	// items left to place - at most O(n). Hard structural ceiling.
	// * Cap by log k: ways is a running product across buckets, and each individual
	// multiplicative step inside combinations() (and each bucket's contribution to
	// ways) strictly increases the accumulated value (every factor >= 1, and in
	// practice binomial coefficients climb fast). Since we only care whether the
	// total exceeds k - and both combinations() internally if(res > k) return k + 1
	// and both combinations interally (if (res > k) return k + 1) and
	// permutations() externally (if(ways > k) break) bail out the intstant that
	// happens - the number of multiplicative steps needed to cross the threshold k
	// is bounded by rought how many "doublings" it takes to exceed k, i.e. O(logk).
	// Once the running product blows past k (<= 10^6), everything downstream stops
	// immediately - we never pay for buckets beyond that point.
	// So whichever limit hits first - running out of items (n) or blowing past k
	// (logk) = caps the work at O(min(n, logk)). Addinf a and b: one permuations()
	// calls costs: O(σ) + O(min(n, logk)) = O(σ + min(n, logk)).
	// Putting it together: We multiply through the levels:
	// O(n) * O(σ) * O(σ + min(n, logk)) = O(n*σ*(σ + min(n, logk)))
	// O(n) for positions, O(σ) for letters tried per positions
	// O(σ + min(n, logk)) - costs of 1 permutations() call.
	// Intuition for why it splits as σ + min(n, logk) rather than σ*min(n, logk).
	// These 2 terms represent genuinely different kinds of work happening in the
	// same pass through the 26-letter loop, not sequential/nest wor:
	// The σ term is "administrative" - just scanning the alphabet to find which
	// letters are present. We pay this no matter how fast the arithmetic finishes.
	// The min(n, logk) term is the total combinatorial computation across the 1
	// scan, capped by early exit. It's a shared budget for the whole permutations()
	// call, not a per-letter costs - because the moment the accumulating costs
	// exceeds k, every remaining letter in the loop is skipped entirely (the
	// break). So it doesn't matter whether there are 5 or 26 nonzero buckets; the
	// arithmetic work is bounded by the same cap regardless.
	// The permutations' calculations is nested syntactically (a for loop over
	// 26-letters, calling a function with its own loop inside). But nested loops
	// only multiply their costs when the inner loop's cost is the same on every
	// outer iteration. Here it's not true - the inner loop's costs shrinks as we
	// go, becuase it's spending down a shared budget.
	// The "n" bound: why summed inner work <= n, not σ*n
	// In combinations(rem, bucket[i], k), the inner loop runs m = min(bucket[i],
	// rem - bucket[i]) times.
	// Now the structural fact: as i advances through the 26 letters, rem shrinks by
	// bucket[i] each time (rem -= bucket[i]). So the buckets are partitioning a
	// fixed total of rem0 <= n items - bucket 'a' takes some of them, bucket 'b'
	// takes the next chunk, etc.
	// Example: rem = 1000, and suppose there happen to be 10 nonzero letters, each
	// with bucket[i] = 100. The inner-loop cost for each call is at most bucket[i]
	// = 100 (ignoring the rem - bucket[i] half for simplicity). Summed across all
	// 10 letters: 100 + 100 + ... + 100 = 1000. Not 10*(worst case per letter) -
	// the sizes themselves are what's being summed, and they can't sum to more than
	// the total pool of 1000 items, no matter how you split them across 26 letters.
	// Compare this to genuine nested-loop cost, e.g. "for each of 26 letters, scan
	// all n items" - that would be 26*n, because each outer iteration independently
	// re-pays the full inner cost. But here each outer iteration only pays for its
	// own slice of a fixed-size pool. Splitting a pie into more slices doesn't make
	// the pie bigger. So: ∑i mi <= rem0 <= n. That's an O(n) total, regardless of
	// whether it's split across 1 letter or 26 letters.
	// The "logk" bound: why it's one shared threshold, not 26 separate ones
	// This is the part where the intuition about "each call starts its own loop
	// from 1 to m" is correct locally, but the thing that stops it early is
	// variable shared across the whole 26-letter loop, not local to each call:
	// ways isn't reset per letter - it accumulates multiplicatively across letters.
	// Each letter's combinations() result gets multiplied in, and the running
	// product only needs to cross the threshold k once, total, for everything to
	// stop.
	// Concretely: suppose letter a contributes a factor of 10, letter b contributes
	// 10, letter c contributes 10, and k ~~ 900. After a: ways = 10. After b: ways
	// = 10*10 = 100. After c: ways = 1000 > k -> break, letters d through z are not
	// touched at all. Only 3 letters were visited, not 26, precisely because the
	// product - not each individual factor - is what's compared to k.
	// This is the crucial difference from independent per-letter work: it's not "26
	// separate races, each running upto logk steps." It's "one race toward k",
	// where each letter contributes 1 leg of the relay." Since each contributing
	// factor is >= 2ish in the worst case that matters (values of 1 don't grow it
	// and are skipped implicitly since bucket[i] == 0 continues), the number of
	// letters needed to multiply the way past k is bounded by roughly logk -
	// regardless of whether that's spread over 3 letters or 20.
	// (Side note: the inner combinations() loop itself also has its own early-exit
	// if(res > k) return k + 1, so even within a single letter's contribution, the
	// steps taken are capped similarly - but this still composes into the same
	// shared, cumulative budget, not a fresh independent one per letter.)
	// Putting the 2 together:
	// Both bounds - the "n" bound and the "logk" bound - are totals across the
	// entire 26-letter loop, not per-iteration costs that get multiplied by 26. The
	// 26-iteration scan is genuinely a separate, fixed O(σ) cost (just visiting
	// each letter to check if(bucket[i] == 0)), and on top of that, the real
	// arithmetic work - wherever it happens to land across those letters - is
	// capped at O(min(n, logk)) in total. So it's O(σ) + O(min(n,logk)) and not:
	// O(σ)*O(min(n,logk)).
	// The multiplication would only be correct if every one of the 26 letters
	// independently paid the full O(min(n,logk)) cost - but they can't, because
	// they're all drawing from the same shrinking pool (rem) and contributing to
	// the same growing product (ways), both of which are shared, cumulative
	// resources rather than per-letter allowances.
	// Time complexity - O(n*σ*(σ + min(n, logk))). For each position in the left
	// half, we enumerate all candidate characters, which costs O(σ). For every
	// candidate, computing the number of remaining permutations takes O(σ + min(n,
	// logk)). Therefore, the total time complexity is O(n*σ*(σ + min(n, logk))).
	// Space complexity - O(1) or O(n), the frequency array bucket occupies O(σ)
	// space, which is constant. The overall space depends on whether the palindrome
	// is constructed in place or by creating additional strings.
	private static String smallestPalindromeMaths(String s, int k) {
		char[] sArr = s.toCharArray();

		int n = sArr.length;
		int partition = n / 2;

		char[] sArrHalf = Arrays.copyOf(sArr, partition);

		int[] count = new int[26];

		for (char c : sArrHalf) {
			count[c - 'a']++;
		}

		StringBuilder left = new StringBuilder();
		int start = 1;

		// At each position we try to put the best character first which has way >=
		// rank. So at each position we try every available character in lexicographical
		// order.
		for (int pos = 0; pos < partition; pos++) {
			for (int i = 0; i < 26; i++) {
				if (count[i] == 0) {
					continue;
				}
				count[i]--;

				long ways = permutations(partition - 1 - pos, count, k);

				// > or >= ?
				if (start + ways > k) {
					left.append((char) (i + 'a'));
					break; // commit to letter i, move to next position
				}

				count[i]++; // undo adding current character, try next letter
				start += ways; // skip past all arrangements starting with letter i.
			}
		}

		if (left.length() < partition) {
			return "";
		}

		// Forced middle char
		if (n % 2 == 1) {
			left.append(sArr[partition]);
		}

		// Mirror the left around center
		for (int i = partition - 1; i >= 0; i--) {
			left.append(left.charAt(i));
		}

		return left.toString();
	}

	private static long permutations(int remaining, int[] count, int k) {
		long ways = 1; // shared across the whole letter-loop

		for (int i = 0; i < 26; i++) {
			if (count[i] == 0) {
				continue;
			}

			ways *= combinations(remaining, count[i], k); // multiplies into the same ways

			// No need to keep multiplying - already too big.
			if (ways > k) { // checked after every letter
				break;
			}

			remaining -= count[i];
		}
		return ways;
	}

	private static long combinations(long n, long m, int k) {
		// use smaller side, standard trick to find combinations faster.
		m = Math.min(m, n - m);

		long result = 1;

		for (int i = 1; i <= m; i++) {
			// We use below instead of result *= (n - i + 1) / i as there is a difference
			// between how the 2 expressions evaluate mathematically different formulas due
			// to operator precedence and integer division in Java.
			// result = result * (n - i + 1) / i; Evaluated from left to right. Multiplies
			// first result * (n - i + 1), then divides by i.
			// result *= (n - i + 1) / i; Evaluated from right to left due to the assignment
			// operator (*=). Divides first then multiplies. (n - i + 1) / i is calculated
			// first. Because of integer division, any remainder is dropped. result is then
			// multiplied by that truncated integer.
			result = result * (n - i + 1) / i;
			// cap - we only care "is this > k?"
			if (result > k) {
				return k + 1;
			}
		}
		return result;
	}

	// Brute Force
	// I'll sort half, find all permutations, get kth palindrome
	// Time complexity - O(2^n)
	// Space complexity - O(2^n)
	public static String smallestPalindromeBruteForce(String s, int k) {
		char[] sArr = s.toCharArray();

		int n = sArr.length;
		int partition = n / 2;

		char[] sArrNew = Arrays.copyOf(sArr, partition);

		// Sorting not needed here, as we sort the arrangement's list at the end.
//		Arrays.sort(sArrNew);

//		int[] count = new int[26];
//		for (char c : sArrNew) {
//			count[c - 'a']++;
//		}

//		int l = 0;
//		for (int i = 0; i < 26; i++) {
//			for (int j = 0; j < count[i]; j++) {
//				sArrNew[l++] = (char) (i + 'a');
//			}
//		}

		Set<String> set = new HashSet<>();
		List<String> list = new ArrayList<>();

		arrangements(sArrNew, 0, list, set);

		if (k > list.size()) {
			return "";
		}

		Collections.sort(list);

		sArrNew = list.get(k - 1).toCharArray();

		int a = 0;
		for (char c : sArrNew) {
			sArr[a] = c;
			sArr[n - 1 - a] = c;
			a++;
		}
		return new String(sArr);
	}

	private static void arrangements(char[] sArrNew, int index, List<String> list, Set<String> set) {
		if (index == sArrNew.length) {
			String str = new String(sArrNew);
			if (set.contains(str)) {
				return;
			}
			list.add(str);
			set.add(str);
		}

		for (int i = index; i < sArrNew.length; i++) {
			swap(sArrNew, index, i);
			arrangements(sArrNew, index + 1, list, set);
			swap(sArrNew, index, i);
		}
	}

	private static void swap(char[] sArrNew, int s, int e) {
		if (s == e) {
			return;
		}
		char temp = sArrNew[s];
		sArrNew[s] = sArrNew[e];
		sArrNew[e] = temp;
	}

}
