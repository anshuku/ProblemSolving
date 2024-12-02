package LeetCode.HashMapSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * P2352. Equal Row and Column Pairs - Medium
 * 
 * Given a 0-indexed n x n integer matrix grid, return the number of pairs (ri, cj) 
 * such that row ri and column cj are equal.
 * A row and column pair is considered equal if they contain the 
 * same elements in the same order (i.e., an equal array).
 * 
 * Approach - HashMap, List, Arrays.equals()
 * 
 * Use of map1.get(index) == map2.get(index) should be avoided
 * as the reference might be different for integer type
 * Use .equals() instead.
 * map1.get(index).equals(map2.get(index))
 */
public class P2352EqualRowColumnPairs {

	public static void main(String[] args) {
//		int[][] grid = { { 3, 2, 1 }, { 1, 7, 6 }, { 2, 7, 7 } };

//		int[][] grid = { { 3, 1, 2, 2 }, { 1, 4, 4, 5 }, { 2, 4, 2, 2 }, { 2, 4, 2, 2 } };

//		int[][] grid = { { 1, 1 }, { 1, 1 } };

		int[][] grid = {
				{ 667, 401, 227, 404, 956, 239, 519, 475, 61, 984, 178, 21, 119, 401, 860, 596, 261, 454, 826, 863, 837,
						79, 401, 194, 771, 1, 238, 771, 926, 401, 110, 818, 401, 483, 350, 166, 701, 684, 631, 290, 668,
						677, 530, 295, 526, 792, 600, 861, 811, 358, 463, 184, 870, 897, 759, 718, 411 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 958, 115, 714, 479, 571, 884, 808, 825, 48, 229, 666, 75, 1000, 115, 262, 482, 861, 208, 863, 419,
						367, 574, 115, 464, 739, 895, 512, 962, 256, 115, 521, 374, 115, 23, 160, 568, 355, 302, 208,
						495, 694, 176, 726, 535, 892, 666, 863, 376, 858, 63, 536, 886, 585, 42, 141, 621, 398 },
				{ 949, 69, 930, 933, 240, 312, 96, 134, 17, 857, 662, 146, 222, 69, 572, 338, 381, 564, 242, 367, 505,
						483, 69, 588, 657, 410, 346, 866, 880, 69, 396, 99, 69, 582, 70, 878, 777, 623, 110, 410, 224,
						666, 545, 362, 812, 942, 713, 64, 169, 486, 997, 146, 975, 792, 181, 210, 544 },
				{ 114, 593, 222, 832, 146, 947, 485, 295, 381, 512, 737, 257, 594, 593, 800, 24, 759, 484, 131, 787,
						344, 695, 593, 126, 876, 516, 930, 748, 172, 593, 607, 221, 593, 245, 403, 330, 512, 108, 214,
						327, 578, 206, 456, 14, 847, 149, 92, 992, 34, 241, 666, 94, 697, 850, 705, 726, 251 },
				{ 965, 972, 580, 399, 208, 151, 823, 431, 913, 158, 285, 34, 531, 972, 818, 234, 737, 894, 176, 162,
						350, 135, 972, 979, 720, 874, 215, 69, 77, 972, 222, 588, 972, 163, 26, 757, 146, 811, 93, 557,
						238, 42, 980, 34, 705, 290, 443, 249, 638, 201, 734, 968, 794, 344, 877, 897, 608 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 318, 199, 720, 745, 773, 514, 873, 191, 55, 234, 682, 925, 980, 199, 843, 394, 247, 199, 361, 310,
						632, 723, 199, 217, 366, 133, 874, 608, 683, 199, 903, 797, 199, 517, 151, 307, 646, 99, 279,
						626, 640, 667, 680, 203, 498, 329, 956, 22, 103, 112, 667, 838, 92, 110, 177, 165, 161 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 974, 294, 83, 693, 641, 411, 594, 145, 147, 899, 266, 452, 152, 294, 499, 506, 306, 397, 203, 12, 252,
						709, 294, 830, 745, 697, 316, 23, 291, 294, 731, 328, 294, 110, 942, 905, 433, 738, 988, 982,
						938, 420, 662, 497, 929, 232, 460, 56, 120, 272, 607, 978, 330, 473, 14, 111, 51 },
				{ 605, 30, 996, 77, 858, 760, 725, 312, 382, 897, 674, 30, 453, 30, 243, 438, 467, 10, 386, 816, 145,
						750, 30, 472, 434, 768, 594, 379, 20, 30, 81, 422, 30, 205, 805, 489, 112, 921, 869, 250, 623,
						829, 595, 705, 768, 14, 909, 399, 506, 641, 332, 486, 827, 62, 891, 100, 564 },
				{ 83, 944, 573, 492, 392, 242, 689, 295, 999, 839, 173, 816, 604, 944, 234, 402, 263, 446, 444, 401,
						980, 666, 944, 178, 500, 462, 513, 384, 326, 944, 370, 926, 944, 678, 570, 347, 950, 897, 987,
						550, 657, 777, 127, 181, 946, 192, 643, 309, 941, 430, 320, 576, 947, 874, 816, 741, 131 },
				{ 231, 61, 538, 121, 612, 803, 923, 90, 276, 925, 111, 109, 85, 61, 499, 220, 165, 341, 510, 96, 937,
						884, 61, 230, 624, 369, 935, 398, 942, 61, 507, 449, 61, 670, 65, 434, 305, 606, 232, 304, 232,
						645, 13, 272, 753, 408, 221, 505, 305, 523, 509, 514, 683, 123, 798, 743, 835 },
				{ 384, 974, 31, 932, 744, 599, 330, 453, 131, 996, 192, 725, 692, 974, 277, 357, 668, 387, 321, 998,
						821, 55, 974, 645, 57, 668, 887, 420, 619, 974, 567, 841, 974, 404, 439, 451, 138, 35, 736, 125,
						338, 331, 524, 61, 680, 234, 570, 509, 940, 466, 181, 982, 488, 820, 336, 912, 205 },
				{ 555, 424, 478, 232, 622, 832, 535, 31, 960, 517, 632, 248, 349, 424, 761, 901, 336, 735, 24, 345, 102,
						291, 424, 261, 885, 267, 215, 303, 579, 424, 335, 88, 424, 825, 892, 95, 566, 567, 454, 755,
						896, 29, 215, 395, 222, 504, 386, 792, 437, 400, 24, 508, 71, 490, 890, 177, 276 },
				{ 912, 610, 469, 134, 89, 715, 329, 586, 907, 968, 31, 563, 143, 610, 951, 789, 140, 856, 674, 730, 348,
						458, 610, 554, 558, 699, 824, 604, 584, 610, 952, 352, 610, 603, 108, 706, 653, 295, 485, 231,
						172, 590, 661, 495, 386, 193, 999, 763, 831, 22, 329, 639, 115, 793, 326, 532, 149 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 994, 10, 238, 785, 525, 649, 980, 15, 70, 539, 12, 726, 418, 10, 691, 899, 541, 435, 101, 417, 488,
						321, 10, 761, 187, 423, 471, 57, 685, 10, 807, 907, 10, 459, 755, 64, 661, 157, 984, 131, 196,
						293, 915, 306, 928, 227, 158, 639, 93, 352, 252, 102, 452, 223, 876, 724, 212 },
				{ 189, 411, 436, 149, 815, 174, 662, 985, 141, 772, 888, 753, 900, 411, 301, 173, 373, 366, 649, 780,
						508, 74, 411, 896, 497, 850, 646, 304, 557, 411, 229, 414, 411, 648, 738, 65, 13, 379, 976, 343,
						416, 426, 246, 959, 910, 464, 604, 842, 610, 34, 824, 963, 113, 497, 52, 88, 47 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 477, 403, 223, 312, 450, 34, 545, 623, 167, 843, 988, 38, 952, 403, 329, 876, 858, 9, 460, 687, 471,
						840, 403, 311, 645, 992, 293, 637, 250, 403, 956, 119, 403, 218, 892, 40, 505, 163, 500, 45,
						222, 792, 299, 487, 350, 917, 928, 479, 46, 795, 441, 357, 466, 31, 776, 388, 677 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 900, 132, 688, 270, 994, 985, 159, 344, 856, 753, 824, 55, 359, 132, 691, 119, 667, 204, 900, 171,
						831, 838, 132, 282, 934, 180, 424, 890, 467, 132, 933, 533, 132, 111, 567, 374, 561, 236, 860,
						737, 55, 124, 118, 945, 677, 7, 89, 329, 847, 733, 912, 30, 744, 923, 301, 735, 671 },
				{ 606, 539, 871, 868, 977, 571, 510, 181, 664, 743, 360, 441, 69, 539, 737, 607, 543, 2, 99, 770, 160,
						261, 539, 819, 985, 532, 189, 899, 939, 539, 240, 582, 539, 2, 351, 62, 676, 905, 402, 80, 660,
						887, 179, 700, 995, 878, 589, 407, 708, 637, 42, 748, 822, 843, 998, 53, 521 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 150, 295, 832, 104, 974, 1000, 586, 592, 399, 19, 204, 864, 735, 295, 257, 12, 89, 562, 609, 652, 939,
						678, 295, 415, 671, 231, 521, 595, 441, 295, 908, 926, 295, 935, 562, 93, 427, 704, 766, 98,
						652, 654, 603, 77, 204, 814, 991, 840, 951, 65, 198, 813, 657, 436, 220, 60, 278 },
				{ 356, 974, 159, 990, 718, 464, 836, 250, 81, 996, 525, 666, 995, 974, 557, 908, 734, 907, 763, 860,
						487, 379, 974, 65, 219, 480, 803, 644, 557, 974, 470, 969, 974, 254, 357, 501, 201, 260, 791,
						191, 833, 605, 571, 318, 685, 4, 267, 433, 897, 427, 687, 590, 516, 822, 907, 526, 384 },
				{ 995, 925, 3, 924, 928, 787, 499, 939, 171, 830, 266, 338, 238, 925, 926, 831, 717, 796, 909, 864, 786,
						830, 925, 32, 511, 64, 60, 136, 608, 925, 913, 665, 925, 31, 786, 859, 202, 642, 636, 575, 777,
						533, 536, 323, 682, 231, 218, 452, 432, 408, 428, 613, 317, 695, 294, 676, 322 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 806, 759, 499, 443, 684, 966, 54, 112, 423, 127, 534, 166, 880, 759, 763, 876, 306, 568, 106, 712,
						817, 967, 759, 253, 413, 108, 940, 904, 33, 759, 720, 562, 759, 732, 422, 488, 313, 510, 31,
						333, 90, 565, 529, 896, 606, 328, 2, 733, 269, 370, 955, 474, 361, 97, 126, 794, 62 },
				{ 977, 824, 981, 866, 647, 395, 969, 102, 539, 675, 935, 590, 772, 824, 775, 521, 986, 837, 342, 855,
						659, 763, 824, 717, 731, 836, 948, 274, 281, 824, 584, 125, 824, 59, 976, 741, 177, 998, 616,
						598, 326, 480, 183, 393, 945, 647, 523, 263, 226, 177, 101, 144, 421, 477, 854, 78, 664 },
				{ 610, 516, 514, 269, 127, 803, 477, 180, 674, 441, 34, 939, 313, 516, 881, 13, 815, 591, 728, 962, 94,
						281, 516, 95, 352, 994, 478, 505, 834, 516, 105, 656, 516, 499, 661, 479, 734, 765, 497, 588,
						202, 346, 689, 471, 911, 45, 621, 868, 217, 662, 312, 795, 418, 985, 18, 755, 420 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 294, 746, 342, 538, 230, 103, 293, 771, 544, 618, 629, 671, 681, 746, 379, 354, 191, 30, 768, 926,
						623, 311, 746, 15, 15, 314, 981, 689, 44, 746, 805, 962, 746, 993, 620, 339, 233, 556, 225, 711,
						788, 403, 652, 624, 115, 837, 698, 692, 938, 183, 753, 13, 787, 764, 786, 50, 133 },
				{ 134, 944, 945, 386, 648, 436, 922, 561, 957, 143, 16, 947, 695, 944, 355, 304, 232, 587, 804, 466,
						264, 816, 944, 872, 594, 114, 829, 901, 838, 944, 315, 249, 944, 671, 267, 93, 489, 733, 165,
						428, 996, 501, 610, 382, 744, 373, 942, 925, 131, 997, 756, 251, 1, 566, 180, 586, 431 },
				{ 817, 704, 428, 568, 76, 256, 614, 700, 867, 105, 641, 504, 673, 704, 785, 126, 842, 104, 499, 678,
						323, 747, 704, 321, 570, 700, 348, 461, 690, 704, 30, 620, 704, 244, 631, 737, 393, 434, 242,
						957, 505, 535, 468, 735, 443, 438, 948, 805, 806, 203, 788, 345, 667, 572, 85, 200, 739 },
				{ 993, 422, 493, 936, 682, 732, 746, 405, 906, 555, 668, 693, 781, 422, 186, 973, 79, 398, 840, 725, 37,
						48, 422, 674, 410, 560, 463, 862, 434, 422, 166, 428, 422, 915, 359, 219, 356, 578, 28, 835,
						264, 965, 736, 289, 436, 829, 570, 738, 440, 764, 652, 753, 440, 808, 724, 284, 172 },
				{ 270, 108, 503, 63, 603, 504, 99, 382, 410, 36, 704, 4, 440, 108, 791, 977, 899, 297, 122, 605, 270,
						805, 108, 239, 984, 487, 238, 777, 367, 108, 495, 175, 108, 48, 407, 772, 656, 553, 757, 775, 6,
						313, 681, 460, 965, 85, 94, 125, 985, 118, 231, 651, 113, 167, 223, 87, 36 },
				{ 492, 712, 753, 360, 452, 792, 888, 413, 158, 14, 129, 191, 480, 712, 422, 122, 252, 287, 780, 253,
						383, 97, 712, 202, 919, 906, 534, 527, 577, 712, 36, 883, 712, 305, 496, 721, 564, 169, 453,
						948, 156, 343, 232, 433, 813, 683, 405, 382, 959, 677, 216, 963, 621, 571, 815, 782, 335 },
				{ 458, 126, 612, 282, 865, 665, 886, 647, 294, 91, 643, 217, 199, 126, 324, 336, 958, 847, 883, 993,
						423, 501, 126, 212, 282, 110, 58, 610, 601, 126, 899, 701, 126, 321, 662, 178, 361, 112, 711,
						39, 146, 703, 907, 412, 514, 845, 824, 7, 360, 702, 949, 795, 86, 590, 216, 596, 856 },
				{ 907, 867, 607, 93, 416, 809, 906, 63, 503, 418, 90, 771, 38, 867, 517, 440, 63, 727, 511, 952, 499,
						660, 867, 866, 739, 144, 880, 967, 310, 867, 150, 735, 867, 240, 243, 618, 406, 767, 637, 949,
						793, 140, 871, 232, 377, 642, 722, 109, 873, 418, 330, 577, 280, 955, 279, 776, 10 },
				{ 663, 968, 196, 754, 620, 277, 597, 938, 600, 700, 281, 278, 753, 968, 646, 349, 317, 864, 107, 105,
						205, 191, 968, 307, 815, 352, 95, 516, 169, 968, 781, 658, 968, 807, 803, 334, 91, 534, 35, 433,
						736, 86, 796, 471, 731, 721, 453, 300, 488, 424, 283, 36, 696, 616, 684, 232, 925 },
				{ 670, 299, 273, 981, 636, 147, 208, 319, 37, 532, 118, 891, 772, 299, 870, 749, 410, 880, 365, 465,
						266, 923, 299, 655, 397, 914, 175, 443, 245, 299, 512, 915, 299, 402, 468, 683, 451, 810, 119,
						556, 297, 962, 396, 573, 417, 503, 264, 11, 491, 938, 940, 581, 429, 117, 428, 684, 881 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 261, 758, 343, 771, 531, 920, 982, 193, 112, 992, 189, 41, 995, 758, 165, 600, 918, 196, 290, 197,
						870, 747, 758, 119, 836, 939, 187, 261, 881, 758, 709, 780, 758, 709, 470, 208, 591, 829, 183,
						256, 707, 3, 334, 255, 691, 553, 375, 525, 148, 200, 406, 500, 785, 11, 327, 934, 291 },
				{ 401, 974, 115, 69, 593, 972, 974, 199, 974, 294, 30, 944, 61, 974, 424, 610, 974, 10, 411, 974, 974,
						403, 974, 974, 974, 132, 539, 974, 295, 974, 925, 974, 974, 974, 974, 759, 824, 516, 974, 974,
						746, 944, 704, 422, 108, 712, 126, 867, 968, 299, 974, 974, 974, 974, 758, 974, 458 },
				{ 951, 458, 615, 168, 177, 936, 827, 295, 531, 375, 343, 956, 356, 458, 606, 463, 251, 136, 746, 56, 68,
						296, 458, 660, 964, 498, 536, 65, 406, 458, 205, 879, 458, 853, 199, 548, 471, 778, 881, 547,
						36, 623, 462, 861, 68, 316, 215, 349, 592, 603, 80, 57, 366, 342, 605, 270, 403 } };

		int pairsMapHash = equalPairsMapHash(grid);
		System.out.println("Map and Hash: The num of equal row and column pairs are - " + pairsMapHash);

		int pairsArr = equalPairsArr(grid);
		System.out.println("Array: The num of equal row and column pairs are - " + pairsArr);

//		int pairsMapArr = equalPairsMapArr(grid);
//		System.out.println("Map and Array: The num of equal row and column pairs are - " + pairsMapArr);

		int pairsMapStr = equalPairsMapStr(grid);
		System.out.println("Map and String: The num of equal row and column pairs are - " + pairsMapStr);

		int pairsList = equalPairsList(grid);
		System.out.println("List: The num of equal row and column pairs are - " + pairsList);

		int pairsMap = equalPairsMap(grid);
		System.out.println("Map: The num of equal row and column pairs are - " + pairsMap);

	}

	private static int equalPairsMapHash(int[][] grid) {
		int n = grid.length;
		Map<Integer, Integer> rowMap = new HashMap<>();
		for (int i = 0; i < n; i++) {
			int rowHash = getRowHash(i, grid);
			rowMap.put(rowHash, rowMap.getOrDefault(rowHash, 0) + 1);
		}
		int pairs = 0;
		for (int i = 0; i < n; i++) {
			int colHash = getColHash(i, grid);
			pairs += rowMap.getOrDefault(colHash, 0);
		}
		return pairs;
	}

	private static int getRowHash(int row, int[][] grid) {
		int rowHash = 0;
		for (int i = 0; i < grid.length; i++) {
			rowHash = grid[row][i] + rowHash * 5;
		}
		return rowHash;
	}

	private static int getColHash(int col, int[][] grid) {
		int colHash = 0;
		for (int i = 0; i < grid.length; i++) {
			colHash = grid[i][col] + colHash * 5;
		}
		return colHash;
	}

	private static int equalPairsArr(int[][] grid) {
		int n = grid.length;
		int[][] grid1 = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				grid1[j][i] = grid[i][j];
			}
		}
		int pairs = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (Arrays.equals(grid[i], grid1[j])) {
					pairs++;
				}
			}
		}
		return pairs;
	}

	// map.get(int[]) and map.getOrDefault(int[]) doesn't works
	private static int equalPairsMapArr(int[][] grid) {
		Map<int[], Integer> rows = new HashMap<>();
		for (int[] row : grid) {
			rows.put(row, rows.getOrDefault(row, 0) + 1);
		}
		int pairs = 0;
		for (int i = 0; i < grid.length; i++) {
			int[] col = new int[grid.length];
			for (int j = 0; j < grid.length; j++) {
				col[j] = grid[j][i];
			}
			pairs += rows.getOrDefault(col, 0);
		}
		return pairs;
	}

	private static int equalPairsMapStr(int[][] grid) {
		Map<String, Integer> rows = new HashMap<>();
		for (int[] row : grid) {
			String rowStr = Arrays.toString(row);
			rows.put(rowStr, rows.getOrDefault(rowStr, 0) + 1);
		}
		int pairs = 0;
		for (int i = 0; i < grid.length; i++) {
			int[] col = new int[grid.length];
			for (int j = 0; j < grid.length; j++) {
				col[j] = grid[j][i];
			}
			String colStr = Arrays.toString(col);
			pairs += rows.getOrDefault(colStr, 0);
		}
		return pairs;
	}

	public static int equalPairsList(int[][] grid) {
		List<List<Integer>> rows = new ArrayList<>();
		List<List<Integer>> cols = new ArrayList<>();
		for (int i = 0; i < grid.length; i++) {
			List<Integer> row = new ArrayList<>();
			List<Integer> col = new ArrayList<>();
			for (int j = 0; j < grid[0].length; j++) {
				row.add(grid[i][j]);
				col.add(grid[j][i]);
			}
			rows.add(row);
			cols.add(col);
		}
		int pair = 0;
		for (List<Integer> row : rows) {
			for (List<Integer> col : cols) {
				if (row.equals(col)) {
					pair++;
				}
			}
		}
		return pair;
	}

	// Doesn't works for large values of row.
	private static int equalPairsMap(int[][] grid) {
		Map<Integer, Map<Integer, Integer>> rows = new HashMap<>();
		int r = grid.length;
		for (int i = 0; i < r; i++) {
			Map<Integer, Integer> row = new HashMap<>();
			for (int j = 0; j < r; j++) {
				row.put(j, grid[i][j]);
			}
			rows.put(i, row);
		}
		Map<Integer, Map<Integer, Integer>> cols = new HashMap<>();
		for (int i = 0; i < r; i++) {
			Map<Integer, Integer> col = new HashMap<>();
			for (int j = 0; j < r; j++) {
				col.put(j, grid[j][i]);
			}
			cols.put(i, col);
		}
		int pairs = 0;
		for (int i = 0; i < r; i++) {
			Map<Integer, Integer> row = rows.get(i);
			for (int j = 0; j < r; j++) {
				Map<Integer, Integer> col = cols.get(j);
				int k = 0;
				// row.get(k) == col.get(k) will fail for higher values of row
				// Use .equals instead
				while ((k < r) && (row.get(k).equals(col.get(k)))) {
					k++;
				}
				if (k == r) {
					pairs++;
				}
			}
		}
		return pairs;
	}

}