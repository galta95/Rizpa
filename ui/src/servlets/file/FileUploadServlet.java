package servlets.file;

import engine.dto.DTOUser;
import engine.stockMarket.StockMarketApi;
import utils.ServletUtils;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import constants.Constants;
import utils.SessionUtils;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class FileUploadServlet extends HttpServlet {


    private static final String SAVE_DIR = "uploadFiles";

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String usernameFromSession = SessionUtils.getUsername(req);
        Part filePart = req.getPart("file");

        if (filePart == null) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().print("invalid file");
            return;
        }

        String appPath = req.getServletContext().getRealPath("");
        String savePath = appPath + SAVE_DIR;
        File fileSaveDir = new File(savePath);

        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

        String tmpFileName = filePart.getSubmittedFileName();
        tmpFileName = new File(tmpFileName).getName();
        filePart.write(savePath + File.separator + tmpFileName);

        File file = new File(savePath + File.separator + tmpFileName);

        StockMarketApi stockMarketApi = ServletUtils.getStockMarketApi(getServletContext());
        DTOUser dtoUser = stockMarketApi.loadXml(usernameFromSession, file.getPath());

        if (dtoUser == null) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().print("invalid file");
            return;
        }

        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().print("The file uploaded successfully!");
    }
}
