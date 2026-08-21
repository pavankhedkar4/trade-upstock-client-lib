To test market feed API create a HTML file as below and check the console in devtools to track live feed data


<!doctype html>
<html>
  <body>
    <script>
      const ws = new WebSocket("ws://localhost:8087/market-feed");

      ws.onopen = () => console.log("✅ Connected");

      ws.onmessage = (event) => {
        console.log("📩 Received:", event.data);
      };

      ws.onerror = (error) => {
        console.error("❌ WebSocket error:", error);
      };

      ws.onclose = (event) => {
        console.log("🔌 Closed:", event.code, event.reason);
      };
    </script>
  </body>
</html>
