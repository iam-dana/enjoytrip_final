var container = document.getElementById('map');
var options = {
  center: new kakao.maps.LatLng(37.502703241291805, 127.04257977647627), // 기본위치 멀티캠퍼스로 초기화
  level: 6,
};

var map = new kakao.maps.Map(container, options);

if (navigator.geolocation) {
  // GeoLocation을 이용해서 접속 위치를 얻어옵니다
  navigator.geolocation.getCurrentPosition(function (position) {
    var lat = position.coords.latitude, // 위도
      lon = position.coords.longitude; // 경도

    var locPosition = new kakao.maps.LatLng(lat, lon); // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다

    // 사용자의 현위치로 지도 setting
    map.setCenter(locPosition);
  });
} else {
  // 현위치 못가져온 경우
  alert('위치 정보를 사용할수 없습니다.');
}
//////////// 지도 초기설정////////////

//////////// 공공데이터 파싱////////////

const serviceKey = import.meta.env.VITE_API_SERVICE_KEY;

// 전국의 시, 도 설정
let areaUrl =
  'https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=' +
  serviceKey +
  '&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json';

fetch(areaUrl, { method: 'GET' })
  .then((response) => response.json())
  .then((data) => makeOption(data));

// 시도 선택 콤보박스 띄우기
function makeOption(data) {
  let areas = data.response.body.items.item;
  let sel = document.getElementById('search-area');
  areas.forEach((area) => {
    let opt = document.createElement('option');
    opt.setAttribute('value', area.code);
    opt.appendChild(document.createTextNode(area.name));

    sel.appendChild(opt);
  });
}

// 검색버튼 눌렀을 때 키워드로 공공데이터 파싱
document.getElementById('btn-search').addEventListener('click', () => {
  let baseUrl = `https://apis.data.go.kr/B551011/KorService1/`;

  let queryString = `serviceKey=${serviceKey}&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;
  let areaCode = document.getElementById('search-area').value;
  let contentTypeId = document.getElementById('search-content-id').value;
  let keyword = document.getElementById('search-keyword').value;

  if (parseInt(areaCode)) queryString += `&areaCode=${areaCode}`;
  if (parseInt(contentTypeId)) queryString += `&contentTypeId=${contentTypeId}`;

  let service = ``;
  if (keyword) {
    service = `searchKeyword1`;
    queryString += `&keyword=${keyword}`;
  } else {
    service = `areaBasedList1`;
  }
  let searchUrl = baseUrl + service + '?' + queryString;

  fetch(searchUrl)
    .then((response) => response.json())
    .then((data) => makeList(data));
});

var positions; // marker에 넣을 제목, 위경도를 담은 배열

// 검색한 데이터를 html에 렌더링
function makeList(data) {
  document.querySelector('table').setAttribute('style', 'display: ;');
  let trips = data.response.body.items.item;
  let tripList = ``;
  positions = [];
  trips.forEach((area) => {
    console.log(area);
    tripList += `
        <tr onclick="moveCenter(${area.mapy}, ${area.mapx});">
          <td><img src="${area.firstimage}" width="100px"></td>
          <td>${area.title}</td>
          <td>${area.addr1} ${area.addr2}</td>
          <td>${area.mapy}</td>
          <td>${area.mapx}</td>
        </tr>
      `;
    // console.log(area);
    let markerInfo = {
      title: area.title,
      latlng: new kakao.maps.LatLng(area.mapy, area.mapx),
    };
    positions.push(markerInfo);
  });
  document.getElementById('trip-list').innerHTML = tripList;
  displayMarker();
}

// 주어진 위경도로 지도 옮기는 함수
function moveCenter(lat, lng) {
  map.setCenter(new kakao.maps.LatLng(lat, lng));
}

// 지도에 관광지 마커 띄우기
function displayMarker() {
  var markers = [];
  for (var i = 0; i < positions.length; i++) {
    var marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: positions[i].latlng, // 마커를 표시할 위치
    });
    markers.push(marker); // 마커 배열에 마커 넣기

    // 마커에 관광지 제목 넣기
    var infowindow = new kakao.maps.InfoWindow({
      content: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
    });

    // 마커에 마우스 올리면 제목 나오고 뗴면 없애기
    kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
    kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
  }

  // 첫번째 검색 정보를 이용하여 지도 중심을 이동
  map.setCenter(positions[0].latlng);

  // 마커 클러스터러를 생성합니다
  var clusterer = new kakao.maps.MarkerClusterer({
    map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
    averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
    minLevel: 10, // 클러스터 할 최소 지도 레벨
    markers: markers, // 클러스터에 마커 추가
  });
}

// 인포윈도우를 표시하는 클로저를 만드는 함수입니다
function makeOverListener(map, marker, infowindow) {
  return function () {
    infowindow.open(map, marker);
  };
}

// 인포윈도우를 닫는 클로저를 만드는 함수입니다
function makeOutListener(infowindow) {
  return function () {
    infowindow.close();
  };
}
