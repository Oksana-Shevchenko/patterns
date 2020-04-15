package com.example.djcontrol;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DJViewServlet", urlPatterns = {"/control"}, loadOnStartup = 1)
public class DJViewServlet extends HttpServlet {
	public void init() {
		BeatModel beatModel = new BeatModel();
		getServletContext().setAttribute("beatModel", beatModel);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BeatModel beatModel = (BeatModel) getServletContext().getAttribute("beatModel");
		getServletContext().setAttribute("beatModel", beatModel);
		RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BeatModel beatModel = (BeatModel) getServletContext().getAttribute("beatModel");
		String bpm = req.getParameter("bpm");
		if (bpm == null) {
			bpm = beatModel.getBPM() + "";
		}
		String set = req.getParameter("set");
		if (set != null) {
			int bpmNumber = 90;
			bpmNumber = Integer.parseInt(bpm);
			beatModel.setBPM(bpmNumber);
		}
		String decrease = req.getParameter("decrease");
		if (decrease != null) {
			beatModel.setBPM(beatModel.getBPM() - 1);
		}
		String increase = req.getParameter("increase");
		if (increase != null) {
			beatModel.setBPM(beatModel.getBPM() + 1);
		}
		String on = req.getParameter("on");
		if (on != null) {
			beatModel.on();
		}
		String off = req.getParameter("off");
		if (off != null) {
			beatModel.off();
		}
		req.setAttribute("beatModel", beatModel);
		RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
		dispatcher.forward(req, resp);
	}
}
