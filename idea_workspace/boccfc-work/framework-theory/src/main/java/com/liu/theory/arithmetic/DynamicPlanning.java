package com.liu.theory.arithmetic;


/**
 * @desc n米，m端。
 * @author Liuweian
 * @version 1.0.0
 * @createTime 2020/1/2 16:28
 */
public class DynamicPlanning {

    /**
     * 动态规划法：
     * 动态规划求解问题的四个特征： 
     *  ①求一个问题的最优解； 
     *  ②整体的问题的最优解是依赖于各个子问题的最优解； 
     *  ③小问题之间还有相互重叠的更小的子问题； 
     *  ④从上往下分析问题，从下往上求解问题；
     *
     * <p>
     * 动态规划：
     *  有一段长度为n的绳子，我们现在要剪第一刀，我可以选择下第一刀的地方有 1~n-1 这些地方；比如长度为10的绳子，
     *  第一刀可以在1~9这些地方下刀，共9种方式。
     *
     *  第一刀下去后，绳子分成两部分，假设在i处下刀，绳子两部分就分别为：[0~i]与[i~n]，长度分为表示为i与n-i；那么
     *  找出第一刀最合适的位置，其实就是找i在哪下刀，可以使得[0~i]与[i~n]的乘积最大，函数表示为：f(n)=max(f(i)×f(n−i))f(n)=max(f(i)×f(n−i))。
     *  那么如何判断i处切最大呢？这个时候，我们就要知道，[0~i]这个长度的绳子，任意方式切，最大的乘积是多少；假如说，
     *  当我们要切一个长度为10的绳子：切成1和9与4和6，两种方式，哪个乘积更大？ 
     *
     * <p>
     * 回答：不光要考虑第一刀后两个绳子的大小，还要考虑到9、4、6这三种情况，因为第一刀切出的绳子长度是否可以再切第二刀，
     *  使它有更大的乘积，比如将9再切成 3×3×3、3×3×3，6切成 4×2 4×2，哪个更大？
     *
     *  这种情况下，我们可以发现，无论再怎么切，一定是越切越短，那么我们是否可以将小于给定长度的绳子的每一个长度的最大乘积都求出来？ 
     *
     * <p>
     * 即：长度为10的绳子，我们就计算出：长度1~9这9种长度的绳子，每种长度的最大乘积是多少。 
     *   要求长度9的绳子的最大乘积，我们要知道1~8各个长度的最大乘积，要知道长度8的最大乘积，就要知道1~7长度的各个最大乘积，以此类推。
     *
     * <p>
     * 动态规划版本
     * f(n)定义为将长度为n的绳子分成若干段后的各段长度的最大乘积（最优解），在剪第一刀时有n-1种剪法，可选择在0 < i < n处下刀
     * 在i处下刀，分成长度为i的左半绳子和长度为n-i的右半绳子，对于这两根绳子，定义最优解为f(i)和f(n-i)，于是f(n) = max(f(i) * f(n-i))，即求出各种相乘可能中的最大值就是f(n)的最优解
     * 就这样从上到下的分下去，但是问题的解决从下到上。即先求出f(2)、f(3)的最优解，然后根据这两个值求出f(4)、f(5)...直到f(n)
     * f(2) = 1，因为只能分成两半
     * f(3) = 2，因为分成两段2*1 大于分成三段的1*1*1
     * ...
     *  
     */

    public static int maxProductAfterCutting(int length) {
        //长度小于2 无法分割
        if (length < 2) {
            return 0;
        }

        // 长度等于2 一分为二 1*1
        if (length == 2) {
            return 1;
        }

        // 长度等于3 最大为1*2=2
        if (length == 3) {
            return 2;
        }

        // 定义一个存放>=4长度的数组，对>=4长度的最大的乘积进行临时存储
        int[] products = new int[length + 1];

        //以下的前三个数组存放的不是最大值，而是长度值
        products[1] = 1;
        products[2] = 2;
        products[3] = 3;

        for (int i = 4; i <= length; i++) {
            int maxModify = 0;

            for (int j = 1; j <= i / 2; j++) {
                int product = products[j] * products[i - j];

                System.out.println(product);
                if (product > maxModify)
                    maxModify = product;
            }

            // 得到f(i)的最优解
            products[i] = maxModify;
        }

        // 返回发f(n)
        return products[length];
    }

    /**
     * 贪婪法，不断分出长度为3的绳子，如果最后只剩下长度为1的绳子，退一步，将得到长度为4的绳子，然后将这长度为4的绳子分成2*2(这样分是因为2*2大于原来的3*1)
     * 因此n = 4时不能分出长度为3的绳子,而n = 2，n = 3可直接返回
     * 当n >=5时候，满足n >=5这个不等式的有2*(n-2) > n以及3*(n-3) > n
     * 注意到2+n-2 = 3+n-3 = n,也就是说分出的两个相乘的数要满足和为n，且同样的n，3*(n-3)的值更大，这就是为什么要不断分出长度为3的绳子的原因
     *  
     */
    public static int maxProductAfterCutting2(int length) {
        // 长度为1时不满足题意，返回0
        if (length < 2) {
            return 0;
        }

        // f(2)
        if (length == 2) {
            return 1;
        }

        // f(3)
        if (length == 3) {
            return 2;
        }

        // 统计能分出多少段长度为3的绳子
        int timesOf3 = length / 3;

        // 如果最有只剩下长度为1的绳子，需要退一步，得到长度为4的绳子，重新分成2*2的
        if (length - timesOf3 * 3 == 1) {
            timesOf3--;
        }
        // 到这步length - timesOf3 * 3的值只可能是0,2,4，所以timesOf2只可能是0, 1, 2
        int timesOf2 = (length - timesOf3 * 3) / 2;
        return (int) Math.pow(3, timesOf3) * (int) Math.pow(2, timesOf2);
    }


    public static void main(String[] args) {
        System.out.println(maxProductAfterCutting(8));
        System.out.println(maxProductAfterCutting2(8));
    }

}
