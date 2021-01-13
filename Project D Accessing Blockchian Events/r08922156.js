// 'use strict';

var fs = require('fs');
const Web3 = require('web3'); 
const quorumjs = require("quorum-js");
const web3 = new Web3("http://foodchain-node3.etherhost.org:22003/");
quorumjs.extend(web3);


function getContractAbi() {
    /* Load food3.abi */ 
    var abi_file = fs.readFileSync('food3.abi','utf-8');
    const CONTRACT_ABI = JSON.parse(abi_file);
    return CONTRACT_ABI;
}

function getContractAdd() {
    /* Load contract address */
    const CONTRACT_ADDRESS = "0xA4fafbE0ea4823e262b4916EF93CC5A6306A5DBc";
    return CONTRACT_ADDRESS;
}

function eventQuery(){
    const CONTRACT_ABI = getContractAbi();
    const CONTRACT_ADDRESS = getContractAdd();

    /*    Code to query events here       */   
    const contract = new web3.eth.Contract(CONTRACT_ABI, CONTRACT_ADDRESS); 

    const START_BLOCK = 0;
    const END_BLOCK = 5000;
    contract.getPastEvents("FoodSection",
    {                               
        fromBlock: START_BLOCK,     
        toBlock: END_BLOCK // You can also specify 'latest'          
    })                              
    .then(events => {
        for (i = 0; i < events.length; i++) {
            if(events[i]['returnValues']['logno']==7477){
                console.log(events[i]);
                // break;
            }
        }
    })
    .catch((err) => console.error(err));

}

eventQuery();

