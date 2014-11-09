
<script type='text/javascript'>
    var order = [];  

      updatePrices = function() {
    	  alert('updatePrices');
      }

      sendOrder = function() {
    	  aler('sendOrder');
        var path = '/sendOrder'+ '?order=' + order;
        var xhr = new XMLHttpRequest();
        xhr.open('POST', path, true);
        xhr.send();
      };


      onOpened = function() {
        var path = '/opened';
        var xhr = new XMLHttpRequest();
        xhr.open('POST', path, true);
        xhr.send();
      };

      onMessage = function(message) {
        prices = JSON.parse(message.data);
        updatPrices();
      }

      openChannel = function() {
        var token = '{{ token }}';
        var channel = new goog.appengine.Channel(token);
        var handler = {
          'onopen': onOpened,
          'onmessage': onMessage,
          'onerror': function() {},
          'onclose': function() {}
        };
        var socket = channel.open(handler);
        socket.onopen = onOpened;
        socket.onmessage = onMessage;
      }

      initialize = function() {
        openChannel();
        //bind to html element
        onMessage({data: '{{ initial_message }}'});
      }

      setTimeout(initialize, 100);

    </script>