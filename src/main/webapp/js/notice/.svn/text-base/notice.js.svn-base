



		  var stopscroll = false;
		  var scrollContHeight = 95; //滚动区域的高度
		  var scrollContWidth = 230; //滚动区域的宽度
		  var scrollSpeed = 25; //这个参数用来确定滚动速度的，数值越小滚动速度越快
		  //获取scrollContainer
		  var scrollContainer = document.getElementById("scrollContainer"); 
		  //获取scrollContent
		  var scrollContent = document.getElementById("scrollContent"); 
		  //多次复制原内容到scrollContainer，直到scrollContainer的高度大于内容区高度
		  //这样做是为了避免内容的高度较小
		  //使用do while可以强制复制一次，这样可以保证滚动看起来更连续
		  do{
			scrollContainer.appendChild( scrollContent.cloneNode(true) );
		  }while( scrollContainer.offsetHeight < scrollContHeight );
		  //下面对scrollContainer进行设置，主要就是宽度和高度，以及换行
		  scrollContainer.style.width = scrollContWidth + "px";
		  scrollContainer.style.height = scrollContHeight + "px";
		  scrollContainer.noWrap = true;
		  //下面是两个事件：[01]鼠标经过，停止滚动[02]鼠标离开，开始滚动
		  scrollContainer.onmouseover = new Function("stopscroll=true;"); 
		  scrollContainer.onmouseout = new Function("stopscroll=false;");   
		  //进行初始化
		  function init(){
			scrollContainer.scrollTop = 0;
			//按时间间隔不断执行函数scrollUp()
			setInterval("scrollUp()", scrollSpeed);
		  }
		  init(); 
		  //下面就是改变滚动位置，如果滚出区域，则判断当前位置进行初始化调整
		  var currTop = 0; 
		  function scrollUp(){
			if(stopscroll == true) return; //如果变量"stopscroll"为真，则停止滚动
			currTop = scrollContainer.scrollTop;
			scrollContainer.scrollTop += 1;//每次上移一个像素
			if(currTop == scrollContainer.scrollTop){
			  //如果滚动到顶了就初始化
			  scrollContainer.scrollTop = 0;
			  scrollContainer.scrollTop += 1;
			}
		  }
		  
		  function close_scroll(ob){
			var obj=document.getElementById(ob);
			if(obj.style.display=="none"){
				obj.style.display="block";
			}else{
				obj.style.display="none";
			}
			
			;
		  }
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  