let map,
  placemark,
  Babynino = [54.396278, 35.735687],
  dataForAndroid,
  myGeocoder;

ymaps.ready(init);

function init() {
  map = new ymaps.Map("map", {
    center: Babynino,
    zoom: 14,
  });
  map.events.add("click", (e) => {
    var coords = e.get("coords");
    if (placemark) placemark.geometry.setCoordinates(coords);
    else {
      placemark = createPlacemark(coords);
      map.geoObjects.add(placemark);
      // Слушаем событие окончания перетаскивания на метке.
      placemark.events.add("dragend", () => {
        getAddress(placemark.geometry.getCoordinates());
      });
    }
    getAddress(coords);
  });

  hideControls(map);
}

// Создание метки.
function createPlacemark(coords) {
  return new ymaps.Placemark(
    coords,
    {
      iconCaption: "поиск...",
    },
    {
      preset: "islands#violetDotIconWithCaption",
      draggable: true,
    }
  );
}

// Определяем адрес по координатам (обратное геокодирование).
function getAddress(coords) {
  placemark.properties.set("iconCaption", "поиск...");
  ymaps.geocode(coords).then(function (res) {
    let firstGeoObject = res.geoObjects.get(0);
    dataForAndroid = {
      coords: coords,
      address: firstGeoObject.properties.get("text"),
    };
    console.log(dataForAndroid);
    placemark.properties.set({
      // Формируем строку с данными об объекте.
      iconCaption: [
        // Название населенного пункта или вышестоящее административно-территориальное образование.
        firstGeoObject.getLocalities().length
          ? firstGeoObject.getLocalities()
          : firstGeoObject.getAdministrativeAreas(),
        // Получаем путь до топонима, если метод вернул null, запрашиваем наименование здания.
        firstGeoObject.getThoroughfare() || firstGeoObject.getPremise(),
      ]
        .filter(Boolean)
        .join(", "),
      // В качестве контента балуна задаем строку с адресом объекта.
      balloonContent: firstGeoObject.getAddressLine(),
    });
  });
}

// Скрываем элементы управления
function hideControls(m) {
  // Скрываем элементы управления
  m.controls.remove("geolocationControl"); // удаляем геолокацию
  m.controls.remove("searchControl"); // удаляем поиск
  m.controls.remove("trafficControl"); // удаляем контроль трафика
  m.controls.remove("typeSelector"); // удаляем тип
  m.controls.remove("fullscreenControl"); // удаляем кнопку перехода в полноэкранный режим
  //map.controls.remove("zoomControl"); // удаляем контрол зуммирования
  m.controls.remove("rulerControl"); // удаляем контрол правил
  m.behaviors.disable(["scrollZoom"]); // отключаем скролл карты (опционально)
}

function returnAddressToAndroid() {
  console.log(dataForAndroid.address);
  android.returnAddress(dataForAndroid.address);
}
