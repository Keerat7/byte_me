Welcome to my implementation of the "Byte Me!" app

Following are the new added Features:
> Customers created are now saved permanently.\
> The order histories of every customer is saved permanently as well.\
> Tests have been added to check selection of items that are not available
> currently as well as for invalid login attempts.\
> A basic GUI has been added to display the latest state of Menu and Pending
> Orders.

Assumptions:
> The GUI will show the latest state of the Menu and Pending Orders if it is
> ran while the CLI is still running.\
> If the CLI has ended, the GUI will show the final state of Menu and Pending
> Orders at the time of termination of CLI.\
> The GUI functions so because it updates itself only when some change occurs
> in the Menu or Pending Orders.\
> Therefore, simply running the CLI won't clear the previous state.\
> \
> For saving data through files, details like cart and current order are not
> retained upon program termination.\
> Only the customer's entry and order history is stored.\
> The items within the past orders get removed from the Menu when the CLI is
> ran fresh as those are not being saved.\
> \
> For Junit testing, a single class handles the tests for out of stock items
> and then another class that handles tests for invalid user login.\
> The OutOfStockTest class checks two test cases, one where an available test
> item is passed and one where an unavailable test item is passed and consequently
> checks if they were allowed to enter the cart or not.\
> The InvalidInputAttemptTest also carries out two tests using a dummy customer.\
> One test correctly enters the credentials and checks if it was successfully
> logged in while other passes wrong credentials and checks if it rejected the
> login.