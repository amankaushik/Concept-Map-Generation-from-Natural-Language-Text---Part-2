jQuery(function($) {
  $('body').addClass('postload');

  $(document).ready(function() {

    $('#header-wrap').waypoint('sticky');

    // Reveal search field

    $('#search .wsite-search-button').click(function(){
      $("#search").toggleClass("showsearch");
      if ($("#search").hasClass("showsearch")) {
          $("#search .wsite-search-input").focus();                
      }
      return false;
    });

  	// Move cart to header

    var cart = $("#navhidden").detach();
    cart.insertAfter('#header');
  	if (!$('.wsite-nav-cart').length) {
  	  $("#search").css({ 'width': '255px', 'padding-right': '0'});
  	}

    // Watch for changes on non-mobile nav

    $('#navhidden').on('DOMSubtreeModified propertychange', function() {
      $("#navhidden li a").each(function(){

        // Differentiating post-load nav elements by the presence of an id (only currently available modifier)
        // Skipping nav cart, which has a different placement in this theme
        if ($(this).attr("id") && ($(this).attr("id") !== "wsite-nav-cart-a")) {
          var navLinkId = $(this).attr("id");
          var navLinkParent = $(this).parent().detach();

          // Append to mobile nav if new element
          if (!$("#nav #"+navLinkId).length) {
            $("#nav .wsite-menu-default").append(navLinkParent);
          }
        }
      });
    });
    

  });

});