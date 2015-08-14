package mergevoucher;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class DelPdfPages {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			delPages();
			//checkPages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void delPages() throws IOException, DocumentException{
		PdfReader reader = new PdfReader("d:/test/t/财务凭证（合）.pdf");		
		Document document = new Document(reader.getPageSizeWithRotation(1));
		String out = "d:/test/t/财务凭证（合）delPayDS.pdf";
		PdfCopy copy =new PdfCopy(document,new FileOutputStream(out));
		document.open();
		int n = reader.getNumberOfPages();
		int count=0;
		
		for(int i=1;i<=n;i++){						
			//merge pages of source pdf
			//filter pages which table name is "个单付费类型日结单"
			String content = PdfTextExtractor.getTextFromPage(reader, i); //读取第i页的文档内容;
			
			//if( content.indexOf("记　账　凭　证")>=0 && content.indexOf("宋潮")<0 ){
			//	System.out.println("Short of sign Page: "+i);
			//}
			if( content.indexOf("个单付费类型日结单")<0 ){ //filter pages "个单付费类型日结单"
				copy.addPage(copy.getImportedPage(reader,i));	
			}	else{
				System.out.println("Delete page:"+i);
				count++;
			}
		}			
		System.out.println("Totally del pages:"+count);
		reader.close();	
		document.close();
		copy.close();
	}
	public static void checkPages() throws IOException, DocumentException{
		
		PdfReader reader = new PdfReader("d:/test/t/财务凭证04.pdf"); //check pdf"审核： 宋潮 记账： 赵慧"
		Document document = new Document(reader.getPageSizeWithRotation(1));		
		document.open();
		int n = reader.getNumberOfPages();
		int count=0;
		
		for(int i=1;i<=n;i++){		
			//filter pages which has no  "审核： 宋潮"or"记账： 赵慧"
			String content = PdfTextExtractor.getTextFromPage(reader, i); //读取第i页的文档内容;	
			int j = content.indexOf("审核： 宋潮");
			int m = content.indexOf("审核：  宋潮");
			int k = content.indexOf("记账：  赵慧");
			
			if( (j<0 && m<0) || k<0 ){
				System.out.println("Short of sign Page: "+i);
				count++;
			}			
		}
		System.out.println("Total short sign pages:"+count);
		reader.close();	
		document.close();		
	}
}
