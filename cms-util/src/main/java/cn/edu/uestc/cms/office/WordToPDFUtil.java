package cn.edu.uestc.cms.office;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


/**
 * word转pdf
 *
 * @author zhangjj
 * @create 2017-11-27 17:28
 **/
public class WordToPDFUtil {

    private static Logger logger = LoggerFactory.getLogger(WordToPDFUtil.class);

    public static void converter(InputStream docStream, OutputStream pdfStream){
        PdfOptions options = PdfOptions.create();
        try {
            XWPFDocument document = new XWPFDocument(docStream);
            PdfConverter.getInstance().convert(document, pdfStream, options);
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    public static void main(String[] args) throws Exception {
        PdfOptions options = PdfOptions.create();
        XWPFDocument document = new XWPFDocument(new FileInputStream(new File("D:/111.docx")));
        PdfConverter.getInstance().convert(document, new FileOutputStream(new File("D:/111.pdf")), options);
    }
}
