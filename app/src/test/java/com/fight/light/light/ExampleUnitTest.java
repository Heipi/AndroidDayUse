package com.fight.light.light;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    private void test(){
        try {
            Class c = Class.forName("com.fight.light.light.ExampleUnitTest");
            Field field =  c.getDeclaredField("msg");
            Object o = c.newInstance();
            field.setAccessible(true);
            Object msg =  field.get(0);
            System.out.print(msg);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    private void sort(int[] array){
        for (int i = 0; i < array.length -1; i++) {
            for (int j = 0; j < array.length - i -1; j++) {
                if (array[j] > array[j+1]){
                    int temp = array[j];
                    array[j] =array[j+1];
                    array[j+1] = temp;
                }
            }
        }
    }



}