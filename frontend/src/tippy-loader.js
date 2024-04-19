// inspired from https://vaadin.com/blog/integrate-jquery-into-vaadin-flow
import tippy from 'tippy.js';
import 'tippy.js/dist/tippy.css';
import 'tippy.js/themes/light-border.css';

(function () {
	function unsetTooltip(el) {
		if (el._tippy) {
			el._tippy.destroy();
			delete el._tippy;
		}
	}

	function setTooltip(el, config) {
		unsetTooltip(el);
		tippy(el, config);
	}

	window.unsetTooltip = unsetTooltip;
	window.setTooltip = setTooltip;
})();
