(function(){
	$.fn.rollpic = function(o){
		var o = $.extend({
			pause:5000,
			nspd:1000,
			uspd:300,
			vnum:5,
			snum:1,
			start:0,
			isH:true,
			auto:true
		}, o||{});
		
		return this.each(function(){
			var $cont = $(".sp-cont", this), 
			$prev = $(".next", this), $next = $(".prev", this),
			$a = $cont.children("li"), len = $a.length, v=o.vnum;
			if(len<v){return false;}
			$cont.prepend($a.slice(len-v-1+1).clone(true)).append($a.slice(0,v).clone(true));
			o.start += v;
			var curr = o.start;
			
			var interval = null, a_dir = o.isH ? "marginLeft":"marginTop", c_dir = "left",
			aSize = o.isH ? $a.outerWidth(true) : $a.outerHeight(true), contDS = o.isH ? "width" : "height",
			itemLength = $cont.children("li").size();
			$cont.css(contDS, itemLength*aSize).css(a_dir, -(curr*aSize));
			
			var isOver = true;
			
			if(o.auto){
				$cont.hover(function(){
					clearInterval(interval);
				}, function(){
					if(c_dir == "left"){
						interval = setInterval(function(){ roll(curr+o.snum)}, o.pause);
					}else if(c_dir == "right"){
						interval = setInterval(function(){ roll(curr-o.snum)}, o.pause);
					}
				});
			}
			if($prev){
				$prev.click(function(){
					if(o.auto){clearInterval(interval)};
					if(isOver==true){roll(curr-o.snum, o.uspd);c_dir = "right";};
					if(o.auto){interval = setInterval(function(){ roll(curr-o.snum)}, o.pause)};
				});
			}
			if($next){
				$next.click(function(){
					if(o.auto){clearInterval(interval)};
					if(isOver==true){roll(curr+o.snum, o.uspd); c_dir = "left";};
					if(o.auto){interval = setInterval(function(){ roll(curr+o.snum)}, o.pause)};
				});
			}
			if(o.auto){ interval = setInterval(function(){ roll(curr+o.snum)}, o.pause);}
			function roll(to, spd){
				if(isOver){
					var spd = spd || o.nspd
					isOver = false;
					if(to<=o.start-v-1){
							$cont.css(a_dir, -((v+(len-v)+curr)*aSize)+"px");
							curr = (v+(len-v)+curr)-o.snum;
					}else if(to>=itemLength-v+1) {
							$cont.css(a_dir, -( (v-(itemLength-v-curr)) * aSize ) + "px" );
							curr = (v-(itemLength-v-curr))+o.snum;
					}else curr = to;
					
					$cont.animate(
							a_dir == "marginLeft" ? {"marginLeft": -(curr*aSize) } : {"marginTop": -(curr*aSize) } , spd, function(){isOver = true;}
					);
				}
				return false;
			};
			
		});
	}
})(jQuery);