package servlets.file;

import engine.stockMarket.StockMarketApi;
import utils.ServletUtils;

import java.io.*;
import java.net.URL;
import java.util.Collection;
import java.util.Scanner;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import javax.xml.bind.JAXBException;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class FileUploadServlet extends HttpServlet {


    private static final String SAVE_DIR = "uploadFiles";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Get the current user
        Part filePart = request.getPart("file");

        if (filePart != null) {
            String appPath = request.getServletContext().getRealPath("");
            String savePath = appPath + SAVE_DIR;
            File fileSaveDir = new File(savePath);

            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }

            Part part = request.getPart("file");

            String tmpFileName = filePart.getSubmittedFileName();
            tmpFileName = new File(tmpFileName).getName();
            part.write(savePath + File.separator + tmpFileName);

            File file = new File(savePath + File.separator + tmpFileName);
            StockMarketApi stockMarketApi = ServletUtils.getStockMarketApi(getServletContext());

            stockMarketApi.loadXml("GAL", file.getPath());
            response.getWriter().print("The file uploaded sucessfully.");
        }
    }
}
