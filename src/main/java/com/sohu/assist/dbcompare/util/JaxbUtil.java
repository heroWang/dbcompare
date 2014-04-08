package com.sohu.assist.dbcompare.util;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

public class JaxbUtil {
    private static final Logger LOG = Logger
            .getLogger(JaxbUtil.class);

    /**
     * 格式化
     *
     * @param r 返回应答对象
     * @return 格式化后的应答信息
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public static String marshal(Object obj){
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(obj.getClass());

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        marshaller.marshal(obj, bos);
        return new String(bos.toByteArray(), "utf-8");
        } catch (JAXBException e) {
            LOG.fatal("obj to string exception:"+e.getMessage());
        } catch (UnsupportedEncodingException e) {
            LOG.fatal(e);
        }
        return null;
    }

    /**
     * 解析返回XML数据
     *
     * @param data 返回数据
     * @return 记录集对象 Rows
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(String data, Class<T> clazz) {
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(clazz);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            // 设置验证XML数据合法性
            unmarshaller.setEventHandler(new JAXBValidator());
            // System.out.println("1:["+data+"]");
            return (T) unmarshaller.unmarshal(new StringReader(data));
        } catch (JAXBException e) {
             LOG.fatal("string to obj exception:"+e.getMessage());
        }
        return null;
    }

}
