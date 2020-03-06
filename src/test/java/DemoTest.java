import org.junit.Test;
import top.xsinfo.cnlp.cshell.Keywords;
import top.xsinfo.cnlp.cshell.common.StringTool;

import java.util.Arrays;

public class DemoTest {
    @Test
    public void test1(){
        String str = "则其名与年龄合并之";
        final String[] strings = StringTool.binString(str, Keywords.KEY_String_center, 0, Keywords.KEY_String_start, Keywords.KEY_String_end);

        System.out.println(Arrays.toString(strings));
    }


}
