package com.cwl.tool.util;

import java.util.*;

/**
 * <pre>
 *
 * </pre>
 *
 * @author chenwl 2019-08-31
 */
public class Arith {

  public static final Random RANDOM = new Random();


  public static final int[] prime(int min, int max, int maxCount) {
    List<Integer> integers = new ArrayList<>(512);
    for (int i = 3, j = 0; i <= max && j < maxCount; i += 2) {
      boolean isPrime = true;
      for (Integer prime : integers) {
        if (i % prime == 0) {
          isPrime = false;
          break;
        }
      }
      if (isPrime) {
        integers.add(i);
        if (i > min) {
          j++;
        }
      }
    }

    Iterator<Integer> iterator = integers.iterator();
    while (iterator.hasNext()) {
      if (iterator.next() < min) {
        iterator.remove();
      } else {
        break;
      }
    }
    int[] primes = new int[integers.size()];
    for (int i = 0, size = integers.size(); i < size; i++) {
      primes[i] = integers.get(i);
    }
    return primes;
  }

  public static final int[] prime(int min, int max) {
    return prime(min, max, Integer.MAX_VALUE);
  }

  public static final int[] primeMaxCount(int min, int maxCount) {
    return prime(min, Integer.MAX_VALUE, maxCount);
  }

  /**
   * 将范围内的素数分组
   *
   * @param min
   * @param max
   * @param eachGroupCount 每组个数，若不能均分，则去掉多余的
   * @return
   */
  public static final int[][] primeGroup(int min, int max, int eachGroupCount) {
    int[] primes = prime(min, max);
    int allCount = primes.length - (primes.length % eachGroupCount);
    int groupSize = allCount / eachGroupCount;
    int[][] groups = new int[groupSize][eachGroupCount];
    for (int i = 0; i < allCount; i++) {
//      均分时，大小也基本一致
      groups[i % groupSize][i / groupSize] = primes[i];
    }
//    随机打乱
    for (int i = 0; i < eachGroupCount; i++) {
      for (int j = 0, len = groups.length, tmp, rdIdx; j < len; j++) {
        rdIdx = RANDOM.nextInt(len);
        tmp = groups[rdIdx][i];
        groups[rdIdx][i] = groups[j][i];
        groups[j][i] = tmp;
      }
    }
    return groups;
  }

  /**
   * 将范围内的素数分组
   *
   * @param min
   * @param groupCount     组数
   * @param eachGroupCount 每组个数，若不能均分，则去掉多余的
   * @return
   */
  public static final int[][] primeGroupByAsc(int min, int groupCount, int eachGroupCount) {
    int allCount = groupCount * eachGroupCount;
    int[] primes = primeMaxCount(min, allCount);
    int[][] groups = new int[groupCount][eachGroupCount];
    for (int i = 0; i < allCount; i++) {
//     挨着顺序均分
      groups[i / eachGroupCount][i % eachGroupCount] = primes[i];
    }
    return groups;
  }
}
