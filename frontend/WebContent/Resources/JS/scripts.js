function getElapsedTime(milliseconds) {
	var years = Math.floor(milliseconds / 3.154e10);
	if (years > 0)
		return years + (years == 1 ? " year" : " years");
	else {
		var months = Math.floor(milliseconds / 2.628e9);
		if (months > 0)
			return months + (months == 1 ? " month" : " months");
		else {
			var days = Math.floor(milliseconds / 8.64e7);
			if (days > 0)
				return days + (days == 1 ? " day" : " days");
			else {
				var hours = Math.floor(milliseconds / 3.6e6);
				if (hours > 0)
					return hours + (hours == 1 ? " hr" : " hrs");
				else {
					var minutes = Math.floor(milliseconds / 60000);
					if (minutes > 0)
						return minutes + (minutes == 1 ? " min" : " mins");
					else
						return "Just now";
				}
			}
		}
	}
}