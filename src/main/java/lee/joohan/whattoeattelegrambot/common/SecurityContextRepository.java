package lee.joohan.whattoeattelegrambot.common;

/**
 * Created by Joohan Lee on 2020/05/18
 */
public class SecurityContextRepository {

//  @Component
//public class SecurityContextRepository implements ServerSecurityContextRepository{
//
//	private static final Logger logger = LoggerFactory.getLogger(SecurityContextRepository.class);
//
//	private static final String TOKEN_PREFIX = "Bearer ";
//
//	@Autowired
//	private AuthenticationManager authenticationManager;
//
//	@Override
//	public Mono save(ServerWebExchange swe, SecurityContext sc) {
//		throw new UnsupportedOperationException("Not supported yet.");
//	}
//
//	@Override
//	public Mono load(ServerWebExchange swe) {
//		ServerHttpRequest request = swe.getRequest();
//		String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//        String authToken = null;
//		if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
//			authToken = authHeader.replace(TOKEN_PREFIX, "");
//		}else {
//			logger.warn("couldn't find bearer string, will ignore the header.");
//		}
//		if (authToken != null) {
//			Authentication auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
//			return this.authenticationManager.authenticate(auth).map((authentication) -> new SecurityContextImpl(authentication));
//		} else {
//			return Mono.empty();
//		}
//	}
//
//}

}
