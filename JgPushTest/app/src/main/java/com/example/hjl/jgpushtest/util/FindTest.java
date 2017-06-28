package com.example.hjl.jgpushtest.util;

import com.example.hjl.jgpushtest.enity.BinCZB;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Administrator on 2017/6/16.
 */

public class FindTest {
    /**
     * 站名字典的检索查询  查询的站名拼音简码
     */
    public static List<BinCZB> FindShezhiZM(InputStream is, String str)
            throws Exception {
        // Document doc = new SAXReader().read(is);
        str = str.trim();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(is));
        NodeList books1 = doc.getElementsByTagName("BHPZMZD0");
        List<BinCZB> li_str = new ArrayList<BinCZB>();
        if (books1 != null) {
            for (int i = 0; i < books1.getLength(); i++) {
                Element element = (Element) books1.item(i);
                if (zh(element.getElementsByTagName("PYM").item(0)).equals(
                        str.toUpperCase())
                        || zh(element.getElementsByTagName("ZM").item(0))
                        .equals(str)) {
                    BinCZB binCZB = new BinCZB();
                    binCZB.setCZID(zh(element.getElementsByTagName("CZID")
                            .item(0)));//
//                    binCZB.setJD(zh(element.getElementsByTagName("JD")
//                            .item(0)));//
//                    binCZB.setWD(zh(element.getElementsByTagName("WD")
//                            .item(0)));//
                    binCZB.setZM(zh(element.getElementsByTagName("ZM")
                            .item(0)));//
                    binCZB.setJM(zh(element.getElementsByTagName("JM")
                            .item(0)));//
//                    binCZB.setJBM(zh(element.getElementsByTagName("JBM")
//                            .item(0)));//
//                    binCZB.setJDM(zh(element.getElementsByTagName("JDM")
//                            .item(0)));//
//                    binCZB.setCZLX(zh(element.getElementsByTagName("CZLX")
//                            .item(0)));//
//                    binCZB.setGJ(zh(element.getElementsByTagName("GJ")
//                            .item(0)));//
                    li_str.add(binCZB);
                }
            }
        }
        return li_str;
    }

    // NULL转换空
    public static String zh(Node book) {

        if (!(book.hasChildNodes())) {
            return "";
        } else {
            return book.getFirstChild().getNodeValue();
        }
    }

}
