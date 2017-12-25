package main;

import java.lang.reflect.Field;

/**
 * Helper class for all Test classes
 */
public class TestHelper {
    
    public static Field getPrivateField(Object entity, String variableName) throws NoSuchFieldException{
        return getPrivateField(entity, entity.getClass(), variableName);
    }
    
    public static Field getPrivateField(Object entity, Class eClass, String variableName) throws NoSuchFieldException{
        try {
            return eClass.getDeclaredField(variableName);
        } catch (NoSuchFieldException | SecurityException ex) {
            Class superClass = eClass.getSuperclass();
            if (superClass==null) throw ex;
            return getPrivateField(entity, superClass, variableName);
        }
    }
    
    public static Object getPrivateVariable(Object entity, String variableName) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        Field privateField = getPrivateField(entity, variableName);
        privateField.setAccessible(true);
        return privateField.get(entity);
    }
            
}
