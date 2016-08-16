var chatGroup;
var chatGroupSort;
$(document).ready(function() {
	var chatGroupJsonStr = $.cookie("chatGroup");

	if (chatGroupJsonStr) {
		chatGroup = JSON.parse(test);
		sortGroupMem(chatGroup);
	} else {
		chatGroup = {};
	}
});

function saveChatGroup(groupId, sendTime) {
	var groupId = groupId + "-";

	if (!chatGroup.groupId) {
		chatGroup[groupId] = sendTime;

		$.cookie("chatGroup", JSON.stringify(chatGroup), {
			expires : 1000000
		});
		
		sortGroupMem(chatGroup);
	}
}

function sortGroupMem(chatGroup) {
	chatGroupSort = [];

	for ( var key in chatGroup) {
		var obj = {
			"key" : key,
			"value" : chatGroup[key]
		};
		chatGroupSort.push(obj);
	}

	chatGroupSort = sortByKey(chatGroupSort, "value");
	console.log(JSON.stringify(chatGroupSort));
}

function sortByKey(array, key) {
	return array.sort(function(a, b) {
		var x = a[key];
		var y = b[key];
		return ((x > y) ? -1 : ((x < y) ? 1 : 0));
	});
}