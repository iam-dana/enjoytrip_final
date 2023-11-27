<template>
    <!-- 중앙 content start -->
    <div class="container">
          <div style="height: 30px"></div>
          <!-- 중앙 center content end -->
          <div class="col-md-9" style="float: none; margin: 0 auto">
            <div class="alert alert-primary text-center fw-bold" role="alert">전국 관광지 정보</div>
            <!-- 관광지 검색 start -->
            <form class="d-flex my-3" onsubmit="return false;" role="search">
              <select id="search-area" class="form-select me-2">
                <option value="0" selected>검색 할 지역 선택</option>
              </select>
              <select id="search-content-id" class="form-select me-2">
                <option value="0" selected>관광지 유형</option>
                <option value="12">관광지</option>
                <option value="14">문화시설</option>
                <option value="15">축제공연행사</option>
                <option value="25">여행코스</option>
                <option value="28">레포츠</option>
                <option value="32">숙박</option>
                <option value="38">쇼핑</option>
                <option value="39">음식점</option>
              </select>
              <input
                id="search-keyword"
                class="form-control me-2"
                type="search"
                placeholder="검색어"
                aria-label="검색어"
              />
              <button id="btn-search" class="btn btn-outline-success" type="button">검색</button>
            </form>
            <!-- kakao map start -->

            <div id="map" class="mt-3" style="width: 100%; height: 400px"></div>
            <!-- kakao map end -->
            <div class="row">
              <table class="table table-striped" style="display: none">
                <thead>
                  <tr>
                    <th>대표이미지</th>
                    <th>관광지명</th>
                    <th>주소</th>
                    <th>위도</th>
                    <th>경도</th>
                  </tr>
                </thead>
                <tbody id="trip-list"></tbody>
              </table>
            </div>
            <!-- 관광지 검색 end -->
          </div>
        </div>
        <!-- 중앙 content end -->
</template>

<style scoped>
#map {
width: 100%;
height: 400px;
}
</style>

<script>
const appKey = import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY;
const serviceKey = import.meta.env.VITE_API_SERVICE_KEY;

export default {
    name: "KakaoMap", // 컴포넌트 이름 지정
    data() {
    return {
        // map 객체 설정
        map: null,
    };
    },
    setup() {},
    created() {},
    mounted() {
    // api 스크립트 소스 불러오기 및 지도 출력
    if (window.kakao && window.kakao.maps) {
        this.loadMap();
    } else {
        this.loadScript();
    }
    },
    unmounted() {},
    methods: {
        // api 불러오기
    loadScript() {
        const script = document.createElement("script");
        script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${appKey}&libraries=services,clusterer`;
        script.onload = () => window.kakao.maps.load(this.loadMap); 

        document.head.appendChild(script);
    },
    // 맵 출력하기
    loadMap() {
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

            
        this.loadMaker();
    },
    // 지정한 위치에 마커 불러오기
    loadMaker() {
        const markerPosition = new window.kakao.maps.LatLng(
        33.450701,
        126.570667
        );

        const marker = new window.kakao.maps.Marker({
        position: markerPosition,
        });

        marker.setMap(this.map);
    },
    },
};

</script>