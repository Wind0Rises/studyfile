package com.liu.nio.study.nio.buffer;

import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Buffer几个重要的成员变量：
 *      mark： 标记的位置。
 *      position：Buffer操作的当前位置。
 *      limit：Buffer最大操作位置。
 *      capacity：Buffer保存的容量。
 */
public class BufferStudy {

    private static final String TEST_PATH = "D:\\test\\test.txt";

    public static void main(String[] args) throws Exception {
        bufferParamTest();
    }

    /**
     * Buffer各个参数测试，以及方法说明。
     * Buffer相当一个容器，就像一个大厅（假设大厅只有一排座位），大厅里有很多座位，如果大厅里有表演，人们会进入大厅里观看表演，为了公平和方便管理，要
     * 求进入大厅的人，必须有序（从左往右）坐入。
     * capacity：表示这个大厅一共有多少个座位。
     * position：表示这一个时刻，第多少个位置已经被占用了。
     * limit：限制只能放多少个元素。比如周杰伦过来演出，来观看的人肯定很多，为了安全，限制只有80%座位的人数过来观看。
     * capacity >= position >= limit
     *
     * flip()：复位操作，把position置位0，
     */
    public static void bufferParamTest() throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        loggerBuffer(byteBuffer);

        // 设置Buffer限制，超出限制会报BufferOverflowException。
        // byteBuffer.limit(5);

        String testData = "0123456789Ab";
        for (int i = 0; i < testData.length(); i++) {
            // 把buffer的position从6置位4。比如某个大领导来了，最好的位置已经被人占住了
            // 于是管理人员通过特殊手段（position方法），把好位置清空了，也可以变大。
            if (i == 6) {
                byteBuffer.position(4);
            }
            String item = testData.substring(i, (i + 1));
            byteBuffer.put(item.getBytes());
            loggerBuffer(byteBuffer);
        }

        /**
         * clear()：把position从新置位0。相当于把大厅清空。mark被抛弃。
         * position(int newPosition)：把操作的当前位置变更一下。
         * mark()：把当前的位置（position）记录一下，记为A。在某个时刻reset()时候，就会把这个时候position（记为B）变更为mark()标记的position（A）。
         * flip()：把当前position赋值给limit，然后把position置为0。比如第一场表演到达一个点发现这个大厅只能正常维护这么多人看表演，
         *         于是把当前的position设置为限额，下一场，只能做这么多人。
         * rewind()：与clear()非常相似
         * remaining()：返回limit - position;就是剩余的位置。
         * hasRemaining()：是否有剩余。返回position < limit。
         */
    }

    public static void fileChannelTest() throws Exception {
        // 获取文件输入流。并生成fileChannel --> FileChannel
        FileInputStream fileInputStream = new FileInputStream(new File(TEST_PATH));
        FileChannel fileChannel = fileInputStream.getChannel();

        // 创建Buffer。
        // ByteBuffer(int mark, int pos, int lim, int cap)
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        // 返回读取元素的个数。将数据从FileChannel读取到Buffer中
        int result = fileChannel.read(byteBuffer);

        System.out.println(new String(byteBuffer.array(), "UTF-8"));
        loggerBuffer(byteBuffer);
    }

    public static void loggerBuffer(Buffer buffer) {
        System.out.println("【日志日志】capacity：" + buffer.capacity() + ";   position：" + buffer.position() +
                ";   mark：" + buffer.mark() + ";    limit：" + buffer.limit());
    }

}
