// inspired from https://vaadin.com/blog/integrate-jquery-into-vaadin-flow
import tippy from 'tippy.js';
import 'tippy.js/dist/tippy.css';
import 'tippy.js/themes/light-border.css';

(function () {
	function setTooltip(el, config) {
		if (el._tippy) {
			el._tippy.destroy();
			delete el._tippy;
		}
		tippy(el, config);
	}

	window.setTooltip = setTooltip;
})();
