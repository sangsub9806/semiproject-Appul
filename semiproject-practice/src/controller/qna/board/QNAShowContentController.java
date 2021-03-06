package controller.qna.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import model.qna.board.QnABoardDAO;
import model.qna.board.QnABoardVO;

public class QNAShowContentController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession(false);
		if (session==null || session.getAttribute("mvo")==null) {
			return "redirect:index.jsp";
		}
		int no=Integer.parseInt(request.getParameter("boardNo"));
		//조회수를 증가시킨다. 
		System.out.println(no);
		QnABoardDAO.getInstance().updateHit(no);
		// 개별 게시물 조회  
		QnABoardVO vo = QnABoardDAO.getInstance().getPostingByNo(no);	
		request.setAttribute("bvo", vo);
		System.out.println(vo);
		return "board/qna_show_content.jsp";
	}
}
