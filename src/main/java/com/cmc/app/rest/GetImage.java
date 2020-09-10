/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.rest;

import com.cmc.app.facade.ParametroFacade;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author 43700118
 */
@WebServlet(name = "Image", urlPatterns = {"/Image/*"})
public class GetImage extends HttpServlet {

    @EJB
    private ParametroFacade parametroFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        FileInputStream in = null;
        OutputStream out = null;
        try {

            File f = new File(parametroFacade.getFilePath(), request.getPathInfo());

            if (!f.exists()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            response.setContentType("image/png");
            response.setContentLength((int) f.length());

            System.out.println("FILE: " + f.getAbsolutePath() + ", " + f.length() / 1024 + "kb");

            /*BufferedImage bi = ImageIO.read(f);
            OutputStream os = response.getOutputStream();
            ImageIO.write(bi, "png", new MemoryCacheImageOutputStream(os));
            IOUtils.closeQuietly(os);*/
            in = new FileInputStream(f);
            out = response.getOutputStream();

            // Copy the contents of the file to the output stream
            byte[] buf = new byte[1024];
            int count = 0;
            while ((count = in.read(buf)) >= 0) {
                out.write(buf, 0, count);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }

    }
}
