package com.gj.easybuy.constant;


/**
 * 公共的枚举集合类
 *
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2018/11/26
 * @Describe
 */
public interface CommonEnum {


    /**
     * 系统状态栏颜色定义
     */
    enum SystemTitleBar {

        white(1, "白色"),
        black(2, "黑色");

        private int value;
        private String name;

        private SystemTitleBar(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 性别
     *
     * @author 高炎
     * @email yan.gao@zarltech.com
     * @create 2018/10/29
     * @Describe
     */
    enum GenderType {

        MAN(0, "男"),
        WOMAN(1, "女");

        private int value;
        private String name;

        GenderType(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }


}
