package com.example.adaptivestreamingplayer.dsa.blind75.arraysAndHashing.containsDuplicate

import com.example.adaptivestreamingplayer.dsa.benchmark

/**
 * 1. ContainsDuplicate
 * * Given an integer array nums, return true if any value appears at least twice in the array, and return false if every element is distinct.
 *
 * Example 1:
 * * Input: nums = [1,2,3,1]
 * * Output: true
 *
 * Explanation:
 * * The element 1 occurs at the indices 0 and 3.
 *
 * Example 2:
 * * Input: nums = [1,2,3,4]
 * * Output: false
 *
 * Explanation:
 * * All elements are distinct.
 *
 * Example 3:
 * * Input: nums = [1,1,1,3,3,4,3,2,4,2]
 * * Output: true
 *
 * Constraints:
 * - 1 <= nums.length <= 105
 * - -109 <= nums[i] <= 109
 */

fun main() {
    val nums = IntArray(100000) { it }

    benchmark("Brute Force", nums) {
        ContainsDuplicate().containsDuplicate(it)
    }
    benchmark("Sorting", nums) {
        ContainsDuplicate().hasDuplicateSorting(it)
    }
    benchmark("Hash Set", nums) {
        ContainsDuplicate().hasDuplicateHashSet(it)
    }
    benchmark("Hash Set Length", nums) {
        ContainsDuplicate().hasDuplicateHashSetLength(it)
    }
}

class ContainsDuplicate {

    /**
     * Given an integer array nums,
     * return true if any value appears at least twice in the array,
     * and return false if every element is distinct.
     */

    /**
     * 1. Brute Force Approach
     * Time & Space Complexity
     * Time complexity: O(n2)
     * Space complexity: O(1)
     */
    fun containsDuplicate(nums: IntArray): Boolean {
        for (i in nums.indices) {
            for (j in i + 1 until nums.size) {
                if (nums[j] == nums[i])
                    return true
            }
        }
        return false
    }

    /**
     * 2. Sorting
     * Time & Space Complexity
     * Time complexity: O ( n log n )
     * Space complexity: O(1) or O (n) depending on the sorting algorithm.
     */
    fun hasDuplicateSorting(nums: IntArray): Boolean {
        nums.sort()
        for(i in 1 until nums.size) {
            if(nums[i] == nums[i-1]) {
                return true
            }
        }
        return false
    }

    /**
     * 3. Hash Set
     * Time & Space Complexity
     * Time complexity: O(n)
     * Space complexity: O(n)
     */
    fun hasDuplicateHashSet(nums: IntArray): Boolean {
        val uniqueNumbers = hashSetOf<Int>()
        for(num in nums) {
            if(num in uniqueNumbers) {
                return true
            }
            uniqueNumbers.add(num)
        }
        return false
    }

    /**
     * 4. Hash Set Length
     * * Time & Space Complexity
     * - Time complexity: O(n)
     * - Space complexity: O(n)
     */
    fun hasDuplicateHashSetLength(nums: IntArray): Boolean {
        return nums.toSet().size < nums.size
    }
}