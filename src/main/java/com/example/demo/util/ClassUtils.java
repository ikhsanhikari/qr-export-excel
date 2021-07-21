package com.example.demo.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author Fauzil Hamdi
 *
 */
public class ClassUtils {

    public final static String THIS_VAR = "this";
    public final static String NULL_VAR = "null";

    /**
     * Memberikan nilai pada field tertentu pada sebuah object
     *
     * @param obj
     * @param fieldName
     * @param value
     * @param accessible
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws SecurityException
     */
    public static void setField(Object obj, String fieldName, Object value,
            boolean accessible) throws NoSuchFieldException,
            IllegalAccessException, SecurityException {
        if (fieldName == null || fieldName.length() == 0) {
            return;
        }
        Field field = obj.getClass().getDeclaredField(fieldName);
        if (field == null) {
            return;
        }
        field.setAccessible(accessible);
        field.set(obj, value);
    }

    /**
     * Mengambil data pada field tertentu pada sebuah object
     *
     * @param obj
     * @param fieldName
     * @param accessible
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws SecurityException
     */
    public static Object getField(Object obj, String fieldName,
            boolean accessible) throws NoSuchFieldException,
            IllegalAccessException, SecurityException {
        if (fieldName == null || fieldName.length() == 0) {
            return null;
        }
        if (THIS_VAR.equals(fieldName)) {
            return obj;
        }
        Field field = obj.getClass().getDeclaredField(fieldName);
        if (field == null) {
            return null;
        }
        field.setAccessible(accessible);
        return field.get(obj);
    }

    /**
     * Mengambil Field tertentu sesuai dengan nama field yang diberikan
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field getFieldDeclaration(Object obj, String fieldName) {
        Field field = null;

        Class<?> clazz = obj.getClass();
        try {
            if (fieldName.indexOf(".") >= 0) {
//				System.out.println(clazz);
                String[] strs = fieldName.split("[.]");
                Field fld1 = clazz.getDeclaredField(strs[0]);
                field = fld1.getType().getDeclaredField(strs[1]);
            } else {
                field = clazz.getDeclaredField(fieldName);
            }
        } catch (SecurityException e) {

            e.printStackTrace();
        } catch (NoSuchFieldException e) {

            e.printStackTrace();
        }

        return field;
    }

    /**
     *
     * Konversi dari satu data type source ke data type lainnya
     *
     * @param source
     * @param destination
     * @return hasil conversi object
     *
     */
    //wonx kasih tambah time pattern 18nov2010
    @SuppressWarnings("unchecked")
//	public static Object convertDataType(Object source, Class destination,boolean isTime) {
    public static Object convertDataType(Object source, Class destination, boolean isTime, String datePattern) {

        Object object = null;

        if (source == null) {
            return source;
        }
//		System.out.println("class utils "+ source + ": " + source.getClass() + " ke " + destination);
        if (source instanceof Boolean) {
            if (destination.getSimpleName().equals("String")) {
                object = ((Boolean) source).toString();
            }

        }

        if (source instanceof BigInteger) {
            if (destination.getSimpleName().equals("String")) {
                String o = String.valueOf(((BigInteger) source).intValue());
                object = o;
            }
            if ((destination.getSimpleName().equals("Integer"))
                    || (destination.getSimpleName().equals("int"))) {
                object = ((BigInteger) source).intValue();
            }
        }

        if (source instanceof BigDecimal) {
            if (destination.getSimpleName().equals("String")) {
//                Nis015MoneyUtils gms = new Nis015MoneyUtils();
//                object = gms.setText(((BigDecimal) source));

            }

            if ((destination.getSimpleName().equals("Integer"))
                    || (destination.getSimpleName().equals("int"))) {
                object = ((BigDecimal) source).intValue();
            }

            if ((destination.getSimpleName().equals("Long"))
                    || (destination.getSimpleName().equals("long"))) {
                object = ((BigDecimal) source).longValue();
            }
        }

        if (source instanceof Long) {
            if (destination.getSimpleName().equals("String")) {
                object = String.valueOf(((Long) source).intValue());
            }

            if ((destination.getSimpleName().equals("Integer"))
                    || (destination.getSimpleName().equals("int"))) {
                object = ((Long) source).intValue();
            }

            if ((destination.getSimpleName().equals("Long"))
                    || (destination.getSimpleName().equals("long"))) {
                object = new BigDecimal((Long) source);
            }

            if (destination.getSimpleName().equals("BigDecimal")) {
                object = new BigDecimal((Long) source);
            }

            if (destination.getSimpleName().equals("Date")) {
//				Long l = ((Long) source) * 1000;
                object = new Date(((Long) source).longValue());
            }
        }

        if (source instanceof Date) {
            if (isTime) {
                if (destination.getSimpleName().equals("String")) {
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                    object = format.format((Date) source);
                }
            } else {
                if (destination.getSimpleName().equals("String")) {
//					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    String tglPattern = "";
                    if (datePattern == null || datePattern.equals("")) {
                        tglPattern = "dd/MM/yyyy";
                    } else {
                        tglPattern = datePattern;
                    }
                    SimpleDateFormat format = new SimpleDateFormat(tglPattern);
                    object = format.format((Date) source);
                } //arya@20180313 mengatasi tipe Timestamp -START-
                else if (destination.getSimpleName().equals("Timestamp")) {

                    Date tmpDate = (Date) source;
                    object = new Timestamp(tmpDate.getTime());
                }
                //arya@20180313 mengatasi tipe Timestamp -END-
            }
        }

        if ((source instanceof Double)
                || (source.getClass().getSimpleName().equals("double"))) {

            if (destination.getSimpleName().equals("String")) {
                object = String.valueOf(((Double) source).doubleValue());
            }

            if (destination.getSimpleName().equals("BigDecimal")) {
//				object = new BigDecimal(((Double) source).doubleValue());
                object = new BigDecimal(((Double) source).toString());
            }

            if ((destination.getSimpleName().equals("Integer"))
                    || (destination.getSimpleName().equals("int"))) {
                object = ((Double) source).intValue();
            }

        }

        if ((source instanceof Integer)
                || (source.getClass().getSimpleName().equals("int"))) {
            if (destination.getSimpleName().equals("String")) {
                object = String.valueOf(source);
            }

            if (destination.getSimpleName().equals("BigDecimal")) {
                object = new BigDecimal((Integer) source);
            }

            if (destination.isEnum()) {
                object = destination.getEnumConstants()[(Integer) source];
            }
        }

        if (source instanceof Character) {
            if (destination.getSimpleName().equals("String")) {
                object = String.valueOf(source);
            } else if ((destination.getSimpleName().equals("Integer"))
                    || (destination.getSimpleName().equals("int"))) {
                object = Integer.parseInt(String.valueOf(source));
            }
        }

        if (source instanceof String) {

            if (destination.getSimpleName().equals("Double")) {
                if (((String) source).length() > 0) {
                    try {
                        object = Double.parseDouble((String) source);
                    } catch (Exception e) {
                        object = 0;
                    }
                } else {
                    object = 0;
                }
            }

            if (destination.getSimpleName().equals("Integer")
                    || destination.getSimpleName().equals("int")) {
                if (((String) source).length() > 0) {
                    try {
                        object = Integer.parseInt((String) source);
                    } catch (Exception e) {
                        object = 0;
                    }
                } else {
                    object = 0;
                }
            }

            if (destination.isEnum()) {
                try {
                    object = destination.getEnumConstants()[Integer.parseInt((String) source)];
                } catch (NumberFormatException e) {
                    object = getEnumz(destination, (String) source);
                }

            }

            if (destination.getSimpleName().equals("Character")) {
                object = String.valueOf(source).charAt(0);
            }

            if (destination.getSimpleName().equals("Date")) {
                String tglPattern = "";
                if (datePattern == null || datePattern.equals("")) {
                    tglPattern = "dd/MM/yyyy";
                } else {
                    tglPattern = datePattern;
                }
                SimpleDateFormat format = new SimpleDateFormat(tglPattern);
                try {
                    object = format.parse(String.valueOf(source));
                } catch (ParseException e) {

                    e.printStackTrace();
                }
            }

            if (destination.getSimpleName().equals("BigDecimal")) {
                String sc = (String) source;
                if (sc.contains(".")) {
                    sc = sc.replace(".", "");
                }
                object = new BigDecimal(sc);
            }
            if (destination.getSimpleName().equals("BigInteger")) {
                String sc = (String) source;
                if (sc.contains(".")) {
                    sc = sc.replace(".", "");
                }
                object = new BigInteger(sc);
            }

            //tambahan jika tipe data year pada mysql
            if (datePattern != null && datePattern.length() > 0 && datePattern.equalsIgnoreCase("yyyy")) {
                object = ((String) source).substring(0, 4);
            }
        }

        if (source.getClass().isEnum()) {
            if (destination.getSimpleName().equals("String")) {
                object = source.toString();
            }

            if (destination.getSimpleName().equals("Integer")
                    || destination.getSimpleName().equals("int")) {
                Object[] t = source.getClass().getEnumConstants();
                object = ((Enum) source).ordinal();
//				for (int i = 0; i < t.length; i++) {
//					if (t[i].toString().equals(source.toString())) {
//						object = i;
//						break;
//					}
////					if (t[i].equals(source)) {
////						object = i;
////						break;
////					}
//				}
            }
        }

        if (object == null) {
            return source;
        }

        return object;
    }

    private static Object getEnumz(Class destination, String source) {
        int length = destination.getEnumConstants().length;
        for (int i = 0; i < length; i++) {
            if (destination.getEnumConstants()[i].toString().equals(source)) {
                return destination.getEnumConstants()[i];
            }
        }
        return null;
    }

    /**
     * Mengambil Field - field yang terdapat annotation tertentu
     *
     * @param <T>
     * @param obj
     * @param annotation
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<Object> getFieldByAnnotation(Object obj,
            Class annotation) {
        List<Object> list = new ArrayList<Object>();

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // System.out.println(field.getName());
            T t = (T) field.getAnnotation(annotation);
//			System.out.println(t);
            try {
                Object value = PropertyUtils.getProperty(obj, field.getName());
                list.add(value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    /**
     * Mengambil string dari annotaion dari element index tertentu
     *
     * @param <T>
     * @param enumerasi
     * @param index
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> String getStringAnnotation(Class<?> enumerasi, int index) {
        String string = "";
        if (enumerasi.isEnum()) {
            T[] t = (T[]) enumerasi.getEnumConstants();

//			if ((index <= t.length) && (index >= 0)) {
            if ((index >= 0)) {

                Method method;
                try {

//					if(index < t.length){
//						method = t[index].getClass().getMethod("getString");
//					}else{
//						method = t[0].getClass().getMethod("getString");
//					}
                    method = t[0].getClass().getMethod("getString");
                    string = (String) method.invoke(t[0], null);
                    if (string.contains("~")) {
                        for (int i = 0; i < t.length; i++) {
                            string = (String) method.invoke(t[i], null);
//							if(string.contains(String.valueOf(index))){
                            String id = string.split("~")[0].trim();
                            if (isIdAngka(id)) {
                                if (id.equals(String.valueOf(index))) {
                                    string = string.split("~")[1].trim();
                                    break;
                                }
                            } else {
                                string = (String) method.invoke(t[index], null);
                                break;
                            }

                        }
                    } else {
                        string = (String) method.invoke(t[index], null);
                    }

                } catch (Exception e) {
                    string = t[index].toString();
                }
            }
        }
        return string;
    }

    @SuppressWarnings("unchecked")
    public static <T> String getStringAnnotation(Class<?> enumerasi, String id) {
        String string = "";
        if (enumerasi.isEnum()) {
            T[] t = (T[]) enumerasi.getEnumConstants();

//			if ((index <= t.length) && (index >= 0)) {
            if ((id.length() >= 1)) {

                Method method;
                try {

//					if(index < t.length){
//						method = t[index].getClass().getMethod("getString");
//					}else{
//						method = t[0].getClass().getMethod("getString");
//					}
                    method = t[0].getClass().getMethod("getString");
                    string = (String) method.invoke(t[0], null);
                    if (string.contains("~")) {
                        for (int i = 0; i < t.length; i++) {
                            string = (String) method.invoke(t[i], null);
                            String aidi = string.split("~")[0].trim();
                            if (id.equals(aidi) || id == aidi) {
                                string = string.split("~")[1].trim();
                                break;
                            }

                        }
                    } else {
                        string = (String) method.invoke(t[Integer.parseInt(id)], null);
                    }

                } catch (Exception e) {
//					string = t[0].toString();
                    string = "";
                }
            }
        }
        return string;
    }

    @SuppressWarnings("unchecked")
    public static <T> String getValueEnum(Class<?> enumerasi, String value) {
        String string = "";
        if (enumerasi.isEnum()) {
            T[] t = (T[]) enumerasi.getEnumConstants();

            if ((value.length() >= 1)) {

                Method method;
                try {

                    method = t[0].getClass().getMethod("getString");
                    for (int i = 0; i < t.length; i++) {
                        string = (String) method.invoke(t[i], null);
                        if (string.contains("~")) {
                            string = string.split("~")[1].trim();

                        }

                        if (value.equalsIgnoreCase(string)) {
                            return string;
                        }

                    }

                } catch (Exception e) {
                    string = "";
                }
            }
        }
        return string;
    }

    private static boolean isIdAngka(String id) {

        boolean cek = true;
        try {
            int parseInt = Integer.parseInt(id);
        } catch (Exception e) {
            // TODO: handle exception
            cek = false;
        }

        return cek;
    }

    /**
     * Membandingkan setiap field pada object dari class yang sama
     *
     * @param source object yang akan dibandingkan
     * @param target object yang dijadikan pembanding
     * @return -1 : error (jika class tidak sama), 0 : nilai setiap field sama,
     * 1 : nilai setiap field tidak semua sama
     */
    public static int compareObject(Object source, Object target) throws Exception {
        int rtr = -1;

        // periksa apakah classnya sama
        if (source.getClass().getName().equals(target.getClass().getName())) {
            rtr = 0;
            // ambil setiap field yang ada pada class tersebut
            Field[] sourceFields = source.getClass().getDeclaredFields();
            for (int i = 0; (i < sourceFields.length) && (rtr == 0); i++) {
                Object object = PropertyUtils.getProperty(source, sourceFields[i].getName());
                Object object2 = PropertyUtils.getProperty(target, sourceFields[i].getName());
                if (!object.equals(object2)) {
                    rtr = 1;
                }
            }
        }

        return rtr;
    }

    /**
     *
     * Fungsi untuk memberikan nilai attribute antara 2 class yang berbeda ato
     * sama, asalkan mempunyai property yang sama
     *
     * @param <T>
     * @param source
     * @param target
     * @return
     */
    public static <T> Object setObjectValue(Object source, Class<T> target) {
        Object object = null;

        try {
            object = target.getConstructor().newInstance();
            // set semua objectnya
            Field[] fields = source.getClass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    Object object2 = PropertyUtils.getProperty(source, field.getName());
//					System.out.println(object2.getClass().getSuperclass().getName());
                    PropertyUtils.setProperty(object, field.getName(), object2);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }

//	public static void main(String[] args) {
//		CompareTestDTO dto			= new CompareTestDTO();
//		dto.setDecimal(new BigDecimal(100));
//		dto.setInteger(50000);
//		dto.setInteger2(200);
//		dto.setString("aaaaa");
//		CompareTestDTO dto2			= new CompareTestDTO();
//		dto2.setDecimal(new BigDecimal(100));
//		dto2.setInteger(50000);
//		dto2.setInteger2(200);
//		dto2.setString("aaaaa");
//		
//		System.out.println(ClassUtils.compareObject(dto, dto2));
//		
//		System.out.println(dto == dto2);
//		
//		CompareTestDTO2 testDTO2	= (CompareTestDTO2) ClassUtils.setObjectValue(dto, CompareTestDTO2.class);
//		System.out.println("testDTO2.getInteger2() : " + testDTO2.getInteger2());
//		System.out.println("testDTO2.getString() : " + testDTO2.getString());
//		System.out.println("testDTO2.getDecimal() : " + testDTO2.getDecimal());
//		
////		System.out.println(convertDataType(PropertiesEnum.BANK_NAME, Integer.class,false));
//	}
}
