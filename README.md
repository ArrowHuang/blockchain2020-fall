# blockchain2020-fall
NTU Blockchain &amp; Data Analytics 2020 Fall

## Project A

Based on the class, everyone should understand that the "coin" or "token" is basically a smart contract on Ethereum blockchain. In this assignment, you will be learning ERC20 protocol by hand-on writing your own smart contract.
Please follow the ERC20 protocol below and launch your own token contract on "Ropsten" Ethereum testnet:

1. function totalSupply() public constant returns (uint);
2. function balanceOf(address tokenOwner) public constant returns (uint balance);
3. function allowance(address tokenOwner, address spender) public constant returns (uint remaining);
4. function transfer(address to, uint tokens) public returns (bool success);
5. function approve(address spender, uint tokens) public returns (bool success);
6. function transferFrom(address from, address to, uint tokens) public returns (bool success);

7. event Transfer(address indexed from, address indexed to, uint tokens);
8. event Approval(address indexed tokenOwner, address indexed spender, uint tokens);


Please name your token after you student ID and send it to the address 0xd2448AC204465aB30e20652421FA70B5DaF8Dd15. After sending the token to specified address, please hand in a document which contains: 1. Your code, 2. The address of contract, 3. screenshot of transaction on Etherscan, to NTUCool (in PDF).

([Reference](https://sites.google.com/site/aiblockonomics/homework/homework1))
  
  
  
## Project C

1st step in Data Analytics: we need to obtain (blockchained) data. 2nd step: Organize them. 3rd step: We analyze data. In this project, we help you do Step 1 already. You do further data organization and analysis in “data.zip”. [“data.zip”] contains 76798 html pages stored in txt file.

(You can also the data from https://fc.efoodex.net/portal.php?oid=1&m=1, …, https://fc.efoodex.net/portal.php?oid=132412&m=1.)

The number in the name of txt file correspods to the oid in URL.
E.g., data_674.txt is the html code from https://fc.efoodex.net/portal.php?oid=674&m=1.

Please name your token after you student ID and send it to the address 0xd2448AC204465aB30e20652421FA70B5DaF8Dd15. After sending the token to specified address, please hand in a document which contains: 1. Your code, 2. The address of contract, 3. screenshot of transaction on Etherscan, to NTUCool (in PDF).

([Reference](https://sites.google.com/site/aiblockonomics/homework/homework2))


## Project D

Foodchain 5 Event Structure

event FoodItem(uint256 indexed logno, string indexed loghash, string indexed logdate);  

event FoodContent(uint256 indexed logno, string indexed logname, string indexed logorg);  

event FoodImage(uint256 indexed logno, string indexed url, string indexed filehash);  

event FoodImageReplace(string indexed url, string indexed oldhash, string indexed newhash);  

event FoodSection(uint256 indexed logno, string title, string indexed begin, string indexed end);  

You need to use web3 to query all past events that a contract has ever emitted.

([Reference](https://sites.google.com/site/aiblockonomics/homework/homework1dueon48))

## Final Project  

Final project is a group project.
At most 4 students in 1 group. If authorized by the professor, you can have one extra student (total 5) in your group, but typically the last person needs to do bonus projects below to justify such a big group.
Each team member needs to justify his or her contribution to the group.

Final project, unlike Project D, is not just about reading Blockchain events (讀鏈). You need to extend it (Option 1) or upgrade it (Option 2: upgrade to 上鏈功能, accompanying 讀鏈).

Option 1) This option is extending Project D. In Project D, our code only need to output the results from getPassEvents. Now, extend it to 查詢介面. How to lookup and the lookup results: Can be app, webpage or CLI.
    a) 製作一個介面，呈現查詢的結果
    b) Then, we should be able to find your transaction through your Dashboard.

Option 2) This option is to deploy an app, demo on 1/8 to take photo with the app. (Of course, you need to fill in 組織，作業 etc. information.)
    Note: If you cannot do an app, use 網頁 or a file instead. But then you need to do at least Bonus 1 to make up.
    Then, we should be able to find your transaction through your Dashboard.

Note: If you only want to do 1) above without 2) above, that means you don't do 上鏈: If you choose to only do simple extension of Project D (In this case you only extend Project D by adding 網頁查詢介面), then you are strongly encouraged to do Bonus 1-3.

Bonus 1: Given one PLTT (logno), show me the number of 作業 and visualize it like a supply chain. Bonus: You should visualize it on a Google Map.  

Bonus 2: LBS: Location-based Service. E.g., what are all the PLTT in Nantou county?  

Bonus 3: You need to enhance the BlockScout Dashboard. Students need to adapt BlockScout Dashboard (https://github.com/poanetwork/blockscout) to make it a PLTT-aware Dashboard. Users should be able to query 品項，組織，作業。Also, users can view 品項-centric view, 組織-centric view, 作業-centric view one-by-one.  


上鏈用的address&private key

 0x7CbEb723CA0788af6549110fb2a9816ED0BAa1a6. 
 
 0xab09158d9a817633c28c74b6e6c1bf34c26ffadc1a961870beaeef38b0753495. 
