package com.example.myviewutils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class ViewUtils {
	
	public static void inject(Activity activity){
		
		/*
		 * 获取字节码
		 */
		Class<Activity> clazz = (Class<Activity>) activity.getClass();
		bindView(activity, clazz);
		bindMethod(activity, clazz);
	}
	//绑定点击事件
	private static void bindMethod(final Activity activity, Class<Activity> clazz) {
		/*
		 * 获取所有方法
		 */
		Method[] methods = clazz.getDeclaredMethods();
		for(final Method method : methods){
			/*
			 * 获取方法上的自定义注解
			 */
			OnClick onClick = method.getAnnotation(OnClick.class);
			if (onClick!=null) {
				/*
				 * 获取id
				 */
				int resId = onClick.value();
				/*
				 * 根据id找到指定的控件
				 */
				final View view = activity.findViewById(resId);//其实就是Button对象
				/*
				 * 给View绑定点击监听事件
				 */
				view.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
//						System.out.println("我被点击了："+v);
						//通过反射调用用户想调用的方法
						method.setAccessible(true);//暴力反射
						try {
							method.invoke(activity, view);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
			
		}
		
	}
	//绑定View
	private static void bindView(Activity activity, Class<Activity> clazz) {
		/*
		 * 获取字节码中所有的字段
		 */
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			/*
			 * 获取field上的注解
			 */
			ViewInject viewInject = field.getAnnotation(ViewInject.class);
			if (viewInject!=null) {
				/*
				 * 获取注解中的值（id 资源的id）
				 */
				int resId = viewInject.value();
				/*
				 * 通过Activity的findViewById查找控件
				 */
				View view = activity.findViewById(resId);
				/*
				 * 把这个控件设置（注入）给当前的字段
				 */
				field.setAccessible(true);
				
				try {
					field.set(activity, view);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}

}
