package com.simlink.core.xml;

import java.beans.*;
import java.util.*;
import java.io.*;

import com.wutka.dtd.*;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** Uses introspection to create a DTD from a Java Bean
 *<br>������Դ��: http://www.wutka.com/ beanToDtd 1.0
 * @author Mark Wutka
 * @version 1.0
 */
public class BeanToDTD {

    private static final Log LOG = LogFactory.getLog(BeanToDTD.class);
    protected boolean fieldsAsAttributes;
    protected TagStyleType tagStyle;

    public DTD makeBeanDTD(Class beanClass, String rootTag) {
        DTD theDTD = new DTD();

        addBeanToDTD(beanClass, theDTD, rootTag);

        return theDTD;
    }

    /** ��������DTD��һ���ļ���. */
    public DTD makeBeanDTD(List<Class> beanClass) {
        DTD theDTD = new DTD();
        for (Class c : beanClass) {
            addBeanToDTD(c, theDTD, getTagName(c.getSimpleName()));
        }
        return theDTD;
    }

    public void setTagStyle(TagStyleType aTagStyle) {
        tagStyle = aTagStyle;
    }

    public TagStyleType getTagStyle() {
        return tagStyle;
    }

    public void setFieldsAsAttributes(boolean flag) {
        fieldsAsAttributes = flag;
    }

    public boolean getFieldsAsAttributes() {
        return fieldsAsAttributes;
    }

    protected void addBeanToDTD(Class beanClass, DTD theDTD, String tagName) {
        //TODO ���ɵ�DTD��Ԫ����Ȼ��ͬ���ظ���.
        if (theDTD.elements.get(tagName) != null) {
            return;
        }

        DTDElement element = new DTDElement();
        element.setName(tagName);

        theDTD.items.addElement(element);
        theDTD.elements.put(element.name, element);

        DTDAttlist attlist = new DTDAttlist();
        attlist.name = element.name;

        theDTD.items.addElement(attlist);

        DTDSequence sequence = new DTDSequence();

        //����List,Map�ȼ�������,ʹ�÷�������ʾ.
        //Fix list
        if (LOG.isDebugEnabled()) {
            LOG.debug("��ǰBean��������:" + beanClass);
        }
        if (List.class.equals(beanClass)) {


            Type type = beanClass.getGenericSuperclass();
            if (type != null && type instanceof ParameterizedType) {
                //Class<T> entityClass = (Class<T>) 

                //ParameterizedType type = (ParameterizedType)beanClass.getGenericSuperclass().getTypeParameters()[0].getGenericDeclaration().getBounds()[0];
                Class subClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
                if (LOG.isDebugEnabled()) {
                    LOG.debug("====================================");
                    LOG.debug("��ǰ����:" + beanClass);
                    LOG.debug("ת�����������(��Ӧ����):" + subClass);
                    LOG.debug("====================================");
                }
                beanClass = subClass;
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("====================================");
                    LOG.debug("��ǰ����:" + beanClass);
                    LOG.debug("û���ҵ�����!");
                    LOG.debug("====================================");
                }
                beanClass = ArrayList.class;
            }



        } else if (Map.class.equals(beanClass)) {     //Fix Map
            beanClass = HashMap.class;
        }
        try {
            BeanInfo info = Introspector.getBeanInfo(beanClass,
                    Object.class);
//            BeanInfo info = Introspector.getBeanInfo(beanClass,
//                    Introspector.USE_ALL_BEANINFO);
            PropertyDescriptor[] props = info.getPropertyDescriptors();

            for (int i = 0; i < props.length; i++) {
                PropertyDescriptor descriptor = props[i];
                //==============================================================
                //������Բ��ܶ�д������.
                //�����֤����ҪĿ������ȥ Collection ���� isEmpty() ����.��������ᱻ����ĵ����Ƕ�������.
                if (descriptor.getReadMethod() == null || descriptor.getWriteMethod() == null){
                    continue;
                }
                //==============================================================
                
                if (fieldsAsAttributes && canBeAttribute(descriptor)) {
                    DTDAttribute attr = new DTDAttribute();
                    attr.name = getTagName(descriptor.getName());
                    attr.type = getAttributeType(descriptor);

                    element.attributes.put(attr.name, attr);
                    attlist.attributes.addElement(attr);
                } else {
                    if (isSimpleType(descriptor)) {
                        DTDElement subElement = createSimpleElement(
                                descriptor);
                        //����ӽڵ��Ƿ��Ѿ����ֹ�,��ֹ�ڵ㶨���ظ�
                        if (!theDTD.elements.containsKey(subElement.name)) {
                            theDTD.elements.put(subElement.name, subElement);
                            theDTD.items.addElement(subElement);

                        } else {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("�ڵ�<" + subElement.name + ">�Ѿ�������,�����Թ�����.");
                            }
                        }
                        
                        DTDName name = new DTDName();
                        name.value = subElement.name;
                        if (descriptor instanceof IndexedPropertyDescriptor) {
                            name.cardinal = DTDCardinal.ZEROMANY;
                        }

                        sequence.add(name);
                    } else {
                        String elemTagName = getTagName(descriptor.getName());

                        //addBeanToDTD(descriptor.getPropertyType(),
                        //theDTD, elemTagName);

                        Class type = getPropertyType(descriptor);
                        if (type != null) {
                            addBeanToDTD(type, theDTD, elemTagName);
                        }

                        DTDName name = new DTDName();
                        name.value = elemTagName;
                        if (descriptor instanceof IndexedPropertyDescriptor) {
                            name.cardinal = DTDCardinal.ZEROMANY;
                        }

                        sequence.add(name);
                    }
                }
            }
        } catch (Exception exc) {
            //exc.printStackTrace();
            LOG.error("BeanToDTD error!", exc);
            return;
        }

        if (sequence.getItemsVec().size() == 0) {
            element.content = new DTDAny();
        } else {
            element.content = sequence;
        }

        if (attlist.attributes.size() == 0) {
            theDTD.items.removeElement(attlist);
        }
    }

    public boolean canBeAttribute(PropertyDescriptor p) {
        if (p instanceof IndexedPropertyDescriptor) {
            return false;
        }

        Class pt = p.getPropertyType();

        if (pt.isPrimitive()) {
            return true;
        }

        if (Integer.class.isAssignableFrom(pt) ||
                Long.class.isAssignableFrom(pt) ||
                Short.class.isAssignableFrom(pt) ||
                Byte.class.isAssignableFrom(pt) ||
                Character.class.isAssignableFrom(pt) ||
                Boolean.class.isAssignableFrom(pt) ||
                Float.class.isAssignableFrom(pt) ||
                Double.class.isAssignableFrom(pt) ||
                String.class.isAssignableFrom(pt) ||
                java.util.Date.class.isAssignableFrom(pt)) {
            return true;
        }

        return false;
    }

    public Object getAttributeType(PropertyDescriptor desc) {
        Class pt = desc.getPropertyType();

        if (Boolean.class.isAssignableFrom(pt)) {
            DTDEnumeration dtdEnum = new DTDEnumeration();
            dtdEnum.add("false");
            dtdEnum.add("true");
            return dtdEnum;
        } else {
            return "CDATA";
        }
    }

    public DTDElement createSimpleElement(PropertyDescriptor desc) {
        DTDElement element = new DTDElement();
        element.name = getTagName(desc.getName());
        DTDMixed mixed = new DTDMixed();
        mixed.add(new DTDPCData());
        element.content = mixed;

        return element;
    }

    public String getTagName(String tagName) {
        if (tagStyle.getSeparator() == TagStyleType.NONE) {
            switch (tagStyle.getCaseType()) {
                case TagStyleType.LOWERCASE:
                    return tagName.toLowerCase();

                case TagStyleType.MIXEDCASE:
                    return tagName;

                case TagStyleType.UPPERCASE:
                    return tagName.toUpperCase();
            }

            return tagName;
        } else {
            String words[] = splitTagWords(tagName);
            StringBuffer buff = new StringBuffer();

            for (int i = 0; i < words.length; i++) {
                if (i > 0) {
                    switch (tagStyle.getSeparator()) {
                        case TagStyleType.HYPHEN:
                            buff.append('-');
                            break;

                        case TagStyleType.UNDERSCORE:
                            buff.append('_');
                            break;
                    }
                }
                switch (tagStyle.getCaseType()) {
                    case TagStyleType.LOWERCASE:
                        buff.append(words[i].toLowerCase());
                        break;

                    case TagStyleType.MIXEDCASE:
                        buff.append(words[i]);
                        break;

                    case TagStyleType.UPPERCASE:
                        buff.append(words[i].toUpperCase());
                        break;
                }
            }

            return buff.toString();
        }
    }

    public boolean isSimpleType(PropertyDescriptor desc) {
        Class pt = desc.getPropertyType();
        if (pt.isArray()) {
            pt = pt.getComponentType();
        }

        if (pt.isPrimitive()) {
            return true;
        }

        if (Integer.class.isAssignableFrom(pt) ||
                Long.class.isAssignableFrom(pt) ||
                Short.class.isAssignableFrom(pt) ||
                Byte.class.isAssignableFrom(pt) ||
                Character.class.isAssignableFrom(pt) ||
                Boolean.class.isAssignableFrom(pt) ||
                Float.class.isAssignableFrom(pt) ||
                Double.class.isAssignableFrom(pt) ||
                String.class.isAssignableFrom(pt) ||
                java.util.Date.class.isAssignableFrom(pt)) {
            return true;
        }

        return false;
    }

    public String[] splitTagWords(String tag) {
        if (tag.indexOf('_') >= 0) {
            StringTokenizer tokenizer = new StringTokenizer(tag, "_");
            Vector words = new Vector();
            while (tokenizer.hasMoreTokens()) {
                words.addElement(tokenizer.nextToken());
            }
            String[] retval = new String[words.size()];
            words.copyInto(retval);
            return retval;
        }
        if (tag.length() == 0) {
            return new String[0];
        }
        if (tag.length() == 1) {
            return new String[]{tag};
        }

        Vector words = new Vector();

        int len = tag.length();
        int startPos = 0;
        int pos = 1;

        char ch1 = tag.charAt(0);

        while (pos < len) {
            char ch2 = tag.charAt(pos);

            if (!Character.isLetter(ch2)) {
                if (Character.isLetter(ch1)) {
                    words.addElement(tag.substring(startPos, pos));
                    startPos = pos;
                    ch1 = ch2;
                    continue;
                } else {
                    ch1 = ch2;
                    pos++;
                }
            } else {
                if (!Character.isLetter(ch1)) {
                    words.addElement(tag.substring(startPos, pos));
                    startPos = pos;
                    ch1 = ch2;
                    continue;
                } else if (Character.isLowerCase(ch1) &&
                        Character.isUpperCase(ch2)) {
                    words.addElement(tag.substring(startPos, pos));
                    startPos = pos;
                    ch1 = ch2;
                    continue;
                } else if (Character.isUpperCase(ch1) &&
                        Character.isLowerCase(ch2)) {
                    if (pos - 1 > startPos) {
                        words.addElement(tag.substring(startPos, pos - 1));
                    }
                    startPos = pos - 1;
                    ch1 = ch2;
                    continue;
                } else {
                    ch1 = ch2;
                    pos++;
                }
            }
        }
        if (startPos < len) {
            words.addElement(tag.substring(startPos, len));
        }

        String[] wordArray = new String[words.size()];
        words.copyInto(wordArray);

        return wordArray;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Format: BeanToDTD [args] classname");
            System.out.println("   Optional arguments:");
            System.out.println("     -lower All tag names lower case");
            System.out.println("     -upper All tag names upper case");
            System.out.println("     -mixed Use original case from bean");
            System.out.println("     -hyphen Changed mixed case names to hyphen-separated");
            System.out.println("         (i.e. firstName becomes first-name)");
            System.out.println("     -under Changed mixed case names to underscore-separated");
            System.out.println("         (i.e. firstName becomes first_name)");
            System.out.println("     -attr Convert properties to attributes when possible");
            System.out.println("     -root tagname  Specify the root tag for the bean");
            System.out.println("         The default is the class name");
            System.out.println("     -file filename Specify the output file name");
            System.out.println("         The default is the class name + '.dtd'");
            System.exit(0);
        }

        try {
            int caseType = 0;
            int separator = 0;
            String filename = null;
            String className = null;
            String rootTag = null;
            boolean attributes = false;

            for (int i = 0; i < args.length; i++) {
                String arg = args[i].toLowerCase();

                if (arg.equals("-lower")) {
                    caseType = TagStyleType.LOWERCASE;
                } else if (arg.equals("-upper")) {
                    caseType = TagStyleType.UPPERCASE;
                } else if (arg.equals("-mixed")) {
                    caseType = TagStyleType.MIXEDCASE;
                } else if (arg.equals("-hyphen")) {
                    separator = TagStyleType.HYPHEN;
                } else if (arg.equals("-under") ||
                        args.equals("-underscore")) {
                    separator = TagStyleType.UNDERSCORE;
                } else if (arg.equals("-attr") ||
                        args.equals("-attributes")) {
                    attributes = true;
                } else if (arg.equals("-file")) {
                    if (i == args.length - 1) {
                        System.err.println("No file given for -file argument");
                        System.exit(1);
                    }
                    filename = args[i + 1];
                    i++;
                } else if (arg.equals("-root")) {
                    if (i == args.length - 1) {
                        System.err.println("No file given for -root argument");
                        System.exit(1);
                    }
                    rootTag = args[i + 1];
                    i++;
                } else if (arg.charAt(0) != '-') {
                    className = args[i];
                } else {
                    System.err.println("Invalid option: " + args[i]);
                    System.exit(1);
                }

            }

            if (className == null) {
                System.err.println("No class name specified");
                System.exit(1);
            }

            if (filename == null) {
                filename = className + ".dtd";
            }

            if (rootTag == null) {
                rootTag = className;
            }

            BeanToDTD converter = new BeanToDTD();
            converter.setFieldsAsAttributes(attributes);
            converter.setTagStyle(new TagStyleType(caseType, separator));

            Class cl = Class.forName(className);
            DTD dtd = converter.makeBeanDTD(cl, rootTag);

            PrintWriter out = new PrintWriter(new FileWriter(filename));
            dtd.write(out);
            out.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    //=====================================================================
    /** ȡ����������.��Ϸ��� */
    private Class getPropertyType(PropertyDescriptor descriptor) {

        //=================================================
        //javaBean������1.6ʱ�Է��ͻ�û�кܺõ�֧��.
        //���,�ڴ˴�ȡ��Bean�������͵ķ�����Ҫ�ر���.
        //��Ա���Ŀ,ÿ������get,set����һ���ǳɶԳ���.����ڴ˿��Թ��ⷽ��ļ��.
        //=================================================

        Class type = descriptor.getPropertyType();
        //����Ǽ�������,�򷵻ط���
        if (type != null &&
                (Collection.class.isAssignableFrom(type))) {
            Method getMethod = descriptor.getReadMethod();  //get method

            Type t = getMethod.getGenericReturnType();
            Class subClass = type;

            //==================================================================
            //2008-6-11 ����
            //TODO Ϊʲôt�޷���ǰȷ���� ParameterizedType ���� Class ? �������ܻ�������.
            //==================================================================

            if (t instanceof ParameterizedType) {
                subClass = (Class) ((ParameterizedType) t).getActualTypeArguments()[0];
            } else if (t instanceof Class) {
                subClass = (Class) t;
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("====================================");
                LOG.debug("��ǰ����:" + descriptor.getName() + " ����:" + type);
                LOG.debug("ת�����������(��Ӧ����):" + subClass);
                LOG.debug("====================================");
            }
            if (!type.equals(subClass)) {
                type = subClass;
            } else {
                //����Set
                if (Set.class.isAssignableFrom(type)){
                    type = HashSet.class;
                }else{
                    //type = null;
                }
            }

        }
        return type;
    }
}
