
package cn.northpark.action;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import cn.northpark.manager.TopicCommentManager;
import cn.northpark.model.TopicComment;
import cn.northpark.query.TopicCommentQuery;
import cn.northpark.query.condition.TopicCommentQueryCondition;
import cn.northpark.utils.MyConstant;
import cn.northpark.utils.PageView;
import cn.northpark.utils.QueryResult;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @author bruce
 * @date 2020-11-29
 * @email zhangyang226@gmail.com
 * @site http://blog.northpark.cn | http://northpark.cn | orginazation https://github.com/jellyband
 * 
 */
@Controller
@RequestMapping("/topiccomment")
public class TopicCommentAction {

private static final Logger LOGGER = Logger
         .getLogger(TopicComment.class);
 @Autowired	
 private TopicCommentManager topiccommentManager;
 @Autowired	
 private TopicCommentQuery topiccommentQuery;

	
	/**
	 * 查看列表
	 * @param map
	 * @param condition
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/list")
	public String list1(ModelMap map,TopicCommentQueryCondition condition,HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws IOException {
		
		String result="/topiccomment";
		String sql = topiccommentQuery.getMixSql(condition);
		
		LOGGER.info("sql ---"+sql);
		
		//定义pageview
		PageView<List<Map<String, Object>>> pageview = new PageView<List<Map<String,Object>>>(1,MyConstant.MAXRESULT);
		
		//获取分页结构不获取数据
		
		pageview = this.topiccommentManager.getMixMapPage(pageview, sql);
		
		map.addAttribute("pageView", pageview);
		map.put("condition", condition);
		map.addAttribute("actionUrl","/topiccomment/list");
		
		

		return result;
	}
	
	

	/**
	 * 查看列表分页
	 * @param map
	 * @param condition
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/list/page/{page}")
	public String list2(ModelMap map,TopicCommentQueryCondition condition, @PathVariable String page,HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws IOException {
		
		String result="/topiccomment";
		String sql = topiccommentQuery.getMixSql(condition);
		
		LOGGER.info("sql ---"+sql);
		int currentpage = Integer.parseInt(page);
		
		//定义pageview
		PageView<List<Map<String, Object>>> pageview = new PageView<List<Map<String,Object>>>(currentpage,MyConstant.MAXRESULT);
		
		//获取分页结构不获取数据
		
		pageview = this.topiccommentManager.getMixMapPage(pageview, sql);
		
		map.addAttribute("pageView", pageview);
		map.put("condition", condition);
		map.addAttribute("actionUrl","/topiccomment/list");
		map.addAttribute("page", page);
		

		return result;
	}
	
	
	
	//异步分页查询数据
	@RequestMapping(value="/query")
	public String plazzquery(ModelMap map,HttpServletRequest request,TopicCommentQueryCondition condition, HttpSession session,String userid) {
		String currentpage = request.getParameter("currentpage");
		
		String sql = topiccommentQuery.getMixSql(condition);
		
		//定义pageview
		PageView<List<Map<String, Object>>> pageview = new PageView<List<Map<String,Object>>>(Integer.parseInt(currentpage),MyConstant.MAXRESULT);
		
		
		//根据分页仅仅获取数据 
		List<Map<String, Object>> list = this.topiccommentManager.findmixByCondition(pageview,sql);
		
		
		
		map.addAttribute("list", list);
		
		
		return "/page/topiccomment/topiccommentdata";
	}
	
	
	
	@RequestMapping("/list3")
	public String list3(ModelMap map,TopicCommentQueryCondition condition, String page,HttpServletRequest request,
			HttpServletResponse response, HttpSession session)  {
		String result="/equp";
		try {
			session.removeAttribute("tabs");
			String whereSql = topiccommentQuery.getSql(condition);
			
			
			//定义pageview
			PageView<TopicComment> pageview  =  new PageView<TopicComment>(1, MyConstant.MAXRESULT); 
			
			LOGGER.info("sql ---"+whereSql);
			
			//排序条件
			LinkedHashMap<String, String> order = new LinkedHashMap<String, String>();
			order.put("date", "desc");
			
			
			QueryResult<TopicComment> qr = this.topiccommentManager.findByCondition(pageview, whereSql, order);
			List<TopicComment> resultlist = qr.getResultlist();
			
			
			//触发生成页码等等
			pageview.setQueryResult(qr);
			map.addAttribute("pageView", pageview);
			map.addAttribute("list", resultlist);
			map.addAttribute("actionUrl","/topiccomment");
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("eqacton------>",e);
		}
		
		

		return result;
	}
	
	@RequestMapping(value="/topiccomment/page/{page}")
	public String list4(ModelMap map,TopicCommentQueryCondition condition, @PathVariable String page,HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws IOException {

		String result="/topiccomment";
		session.removeAttribute("tabs");
		String whereSql = topiccommentQuery.getSql(condition);
		
		
		
		//定义pageview
		PageView<TopicComment> pageview  =  new PageView<TopicComment>(Integer.parseInt(page), MyConstant.MAXRESULT); 
		
		String keyword = request.getParameter("keyword");
		if(StringUtils.isNotEmpty(keyword)){
			keyword = WAQ.forSQL().escapeSql(keyword);
			whereSql+=" and title like '%"+keyword+"%' ";
			
			map.addAttribute("keyword", keyword);
			
		}
		LOGGER.info("sql ---"+whereSql);
		
		//排序条件
		LinkedHashMap<String, String> order = new LinkedHashMap<String, String>();
		order.put("date", "desc");
		
		
		QueryResult<TopicComment> qr = this.topiccommentManager.findByCondition(pageview, whereSql, order);
		List<TopicComment> resultlist = qr.getResultlist();
		//触发生成页码等等
		pageview.setQueryResult(qr);
		map.addAttribute("pageView", pageview);
		map.addAttribute("list", resultlist);
		map.addAttribute("actionUrl","/romeo");
		map.addAttribute("page", page);
		

		return result;
	}

}
