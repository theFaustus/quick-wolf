
	var itineraryStepCounter = 0;

	document.getElementById("itineraryStepButton").onclick = function (e) {
		e.preventDefault();
		let container = document.getElementById("itineraryStepsContainer");
        const itineraryStepDivId = "itineraryStep_" + itineraryStepCounter;
        const itineraryStepDiv = $(`<div id="${itineraryStepDivId}" class="col-md-12">`);
        const removeButton = $(`<button class="btn_full" style="background-color: red;">Remove step</button>`);
        $(removeButton).click(function () {
            $("#" + itineraryStepDivId).remove();
        });
        createItineraryElement("Step name: ", "name", itineraryStepDiv, "form-group col-md-4");
        createItineraryDateTimeElement("Arrive date: ", "arrive", itineraryStepDiv, "form-group col-md-2", "date-pick");
        createItineraryDateTimeElement("Arrive time: ", "arriveTime", itineraryStepDiv, "form-group col-md-2", "time-pick");
        createItineraryDateTimeElement("Depart date: ", "depart", itineraryStepDiv, "form-group col-md-2", "date-pick");
        createItineraryDateTimeElement("Depart time: ", "departTime", itineraryStepDiv, "form-group col-md-2", "time-pick");
        createItineraryElement("Country: ", "country", itineraryStepDiv, "form-group col-md-4");
        createItineraryElement("City: ", "city", itineraryStepDiv, "form-group col-md-4");
        createItineraryElement("Street: ", "street", itineraryStepDiv, "form-group col-md-4");
        itineraryStepDiv.append(removeButton);
        itineraryStepDiv.append(document.createElement("hr"));
		$(container).append(itineraryStepDiv);
		$('input.date-pick').filter(function(){
			return !($(this).attr('data-trip-master') == "true");
		}).datepicker('setDate', 'today');
		$('input.time-pick').filter(function(){
			return !($(this).attr('data-trip-master') == "true");
		}).timepicker({
			use24hours: true,
			timeFormat: 'H:mm:ss',
			showMeridian: false,
			minuteStep: 1,
			showInpunts: false
		});
		++itineraryStepCounter;
	};

	function createItineraryElement(greeting, propertyName, container, howManyCols) {
		let stepWrapper = document.createElement("div");
		let labelWrapper = document.createElement("label");
		stepWrapper.className = howManyCols;
		container.append($(stepWrapper));
		labelWrapper.appendChild(document.createTextNode(greeting));
		stepWrapper.appendChild(labelWrapper);
		stepWrapper.appendChild(createInputElement(getNamePrefix() + propertyName, stepWrapper));
	}

	function createItineraryDateTimeElement(greeting, propertyName, container, howManyCols, kind) {
		let stepWrapper = document.createElement("div");
		let labelWrapper = document.createElement("label");
		stepWrapper.className = howManyCols;
		container.append($(stepWrapper));
		labelWrapper.appendChild(document.createTextNode(greeting));
		stepWrapper.appendChild(labelWrapper);
		stepWrapper.appendChild(createInputDateTimeElement(getNamePrefix() + propertyName, stepWrapper, kind));
	}

	function getNamePrefix() {
		return "itinerarySteps[" + itineraryStepCounter + "].";
	}

	function createInputElement(nameAttribute, parent) {
		let element = document.createElement("input");
		parent.appendChild(element);
		parent.appendChild(document.createElement("br"));
		element.type = "text";
		element.className = "form-control ";
		element.name = nameAttribute;
		element.id = nameAttribute;
		return element;
	}

	function createInputDateTimeElement(nameAttribute, parent, kind) {
		let element = document.createElement("input");
		parent.appendChild(element);
		parent.appendChild(document.createElement("br"));
		element.className = kind.concat(" form-control");
		if(kind == "date-pick"){
			element.dataset.dateFormat = "m/d/yyyy";
		} else {
			element.dataset.dateFormat = "H:mm:ss";
		}
		element.type = "text";
		element.name = nameAttribute;
		element.id = nameAttribute;
		return element;
	}
